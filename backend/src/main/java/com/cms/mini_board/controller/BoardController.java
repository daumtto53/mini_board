package com.cms.mini_board.controller;

import com.cms.mini_board.dto.BoardPageDTO;
import com.cms.mini_board.dto.BoardReadDTO;
import com.cms.mini_board.dto.PageDTO.PageRequestDTO;
import com.cms.mini_board.dto.PageDTO.PageResultDTO;
import com.cms.mini_board.dto.PostDTO;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.service.PostService;
import com.cms.mini_board.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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

    @GetMapping("")
    //need to change to return response entity
    public PageResultDTO<BoardPageDTO, Post> boardList(@RequestParam String pageNum) {
        PageRequestDTO req = PageRequestDTO.builder()
                .offset(Integer.valueOf(pageNum))
                .size(10).build();
        PageResultDTO<BoardPageDTO, Post> dto = postService.getList(req);
        return dto;
    }

    //file이 attached 되었는지 되지 않았는지에 따라 분기함.
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Long> writePost(
            @RequestPart(value = "dto") PostDTO postDTO,
            @RequestPart(required = false) List<MultipartFile> file) {
        Long saved;
        log.info("isFileAttached: {}", postDTO.isFileAttatched());
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
        log.info(postDTO);
        log.info("modifyPost = {}", file);
        Long l = postService.modifyPost(postDTO, file);
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
        log.info("fullPath = {}", fullPath);
        Resource resource = new UrlResource("file:" + fullPath);
        if (!resource.exists()) {
            log.info("not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(fullPath);
            log.info("filePath = {}", filePath);
            header.add("Content-Type", Files.probeContentType(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }


}
