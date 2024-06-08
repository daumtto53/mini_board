package com.cms.mini_board.controller;

import com.cms.mini_board.dto.*;
import com.cms.mini_board.dto.PageDTO.PageRequestDTO;
import com.cms.mini_board.dto.PageDTO.PageResultDTO;
import com.cms.mini_board.entity.Enum.SearchOption;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.service.PostService;
import com.cms.mini_board.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Log4j2
public class BoardController {

    private final PostService postService;
    private final FileUtils fileUtils;

    private SearchOption convertOptionToSearchOption(String option) {
        if (option.equals("title"))
            return SearchOption.TITLE;
        else if (option.equals("content") )
            return SearchOption.CONTENT;
        else if (option.equals("title_content"))
            return SearchOption.TITLE_CONTENT;
        else
            return null;
    }

    @GetMapping("")
    //need to change to return response entity
    public PageResultDTO<BoardPageDTO, Post> boardList(
            @RequestParam String pageNum,
            @RequestParam(required = false) String option,
            @RequestParam(required = false) String searchQuery
    ) {
        //QueryParameter로 search가 들어왔을때와,
        //단순 load request가 왔을 때 분리해서 실행한다.
        PageRequestDTO req = PageRequestDTO.builder()
                .offset(Integer.valueOf(pageNum))
                .size(10).build();

        PageResultDTO<BoardPageDTO, Post> dto;
        if (option == null)
            dto = postService.getList(req);
        else
            dto = postService.getList( req,
                    SearchCondition.builder()
                            .searchOption(convertOptionToSearchOption(option))
                            .searchQuery(searchQuery)
                            .build());
        return dto;
    }

    //file이 attached 되었는지 되지 않았는지에 따라 분기함.
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Long> writePost(
            @RequestPart(value = "dto") PostDTO postDTO,
            @RequestPart(required = false) List<MultipartFile> file) {
        Long saved;
        if (postDTO.isFileAttatched())
            saved = postService.writePost(postDTO, file);
        else
            saved = postService.writePost(postDTO);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    //boardRead
    @GetMapping("/{postId}")
    public ResponseEntity<BoardReadDTO> readPost(@PathVariable String postId) throws NotFoundException {
        postService.incrementPostViewCount(Long.valueOf(postId));
        BoardReadDTO dto = postService.getFullBoardReadContent(postId);
        return new ResponseEntity<BoardReadDTO>(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/{postId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> modifyPost(
            @RequestPart(value = "dto") PostDTO postDTO,
            @RequestPart(required = false) List<MultipartFile> file) {
        Long saved;
        if (postDTO.isFileAttatched())
            postService.modifyPost(postDTO, file);
        else
            postService.modifyPost(postDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId) {
        postService.deletePost(Long.valueOf(postId));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /* single board image get from post */
    @GetMapping("/image/{date}/{savedName}")
    public ResponseEntity<Resource> getBoardImage(@PathVariable String date, @PathVariable String savedName) throws MalformedURLException {
        String basicPath = fileUtils.getBasicPath();
        String fullPath = basicPath + File.separator +  date + File.separator + savedName;
        Resource resource = new UrlResource("file:" + fullPath);
        if (!resource.exists()) {
            log.info("not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(fullPath);
            header.add("Content-Type", Files.probeContentType(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

    @GetMapping("/image/attach/{date}/{savedName}")
    public ResponseEntity<Resource> downloadBoardFileByATag(@PathVariable String date,
                                                      @PathVariable String savedName) {
        Path fullPath = Path.of(fileUtils.getBasicPath(), date, savedName);
        BoardFileDTO boardFileDTO = postService.getDownloadFileDTO(savedName);
        UrlResource resource = null;
        try {
            resource = new UrlResource("file:" + fullPath);
        } catch (MalformedURLException e) {e.printStackTrace();}
        String originalFileName = boardFileDTO.getOriginalFileName();
        String encodedOriginalFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    private byte[] getFileByteStream(Path fullPath) {
        try {
            return Files.readAllBytes(fullPath);
        } catch (IOException e) {throw new RuntimeException(e.getMessage(), e);}
    }
    private ResponseEntity<byte[]> createResponseEntityForFileDownload(
            String encodedFileName,byte[] byteStream) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", encodedFileName);
        return ResponseEntity.ok()
                .headers(headers)
                .body(byteStream);
    }
    @GetMapping("/image/byteattach/{date}/{savedName}")
    public ResponseEntity<byte[]> downloadboardFileByByteStream(@PathVariable String date,
                                                      @PathVariable String savedName) {
        BoardFileDTO downloadFileDTO = postService.getDownloadFileDTO(savedName);
        Path fullPath = Path.of(fileUtils.getBasicPath(), date, savedName);
        byte[] fileByteStream = getFileByteStream(fullPath);
        String encodedFileName = URLEncoder.encode(downloadFileDTO.getOriginalFileName(), StandardCharsets.UTF_8);
        return createResponseEntityForFileDownload(encodedFileName, fileByteStream);
    }
}
