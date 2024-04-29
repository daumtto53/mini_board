package com.cms.mini_board.controller;

import com.cms.mini_board.dto.BoardPageDTO;
import com.cms.mini_board.dto.BoardReadDTO;
import com.cms.mini_board.dto.PageDTO.PageRequestDTO;
import com.cms.mini_board.dto.PageDTO.PageResultDTO;
import com.cms.mini_board.dto.PostDTO;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Log4j2
public class BoardController {

    private final PostService postService;

    @GetMapping("")
    //need to change to return response entity
    public PageResultDTO<BoardPageDTO, Post> boardList(@RequestParam String pageNum) {
        PageRequestDTO req = PageRequestDTO.builder()
                .offset(Integer.valueOf(pageNum))
                .size(10).build();
        PageResultDTO<BoardPageDTO, Post> dto = postService.getList(req);
        return dto;
    }

    @PostMapping("")
    public ResponseEntity<Long> writePost(@RequestBody PostDTO postDTO) {
        Long saved = postService.writePost(postDTO);
        log.info("writePost={}", postDTO);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    //boardRead
    @GetMapping("/{postId}")
    public ResponseEntity<BoardReadDTO> readPost(@PathVariable String postId) throws NotFoundException {
        BoardReadDTO dto = postService.getFullBoardReadContent(postId);
        return new ResponseEntity<BoardReadDTO>(dto, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> modifyPost(@RequestBody PostDTO postDTO) {
        Long l = postService.modifyPost(postDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId) {
        postService.deletePost(Long.valueOf(postId));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
