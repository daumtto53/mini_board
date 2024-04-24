package com.cms.mini_board.controller;

import com.cms.mini_board.dto.BoardPageDTO;
import com.cms.mini_board.dto.BoardReadDTO;
import com.cms.mini_board.dto.PageDTO.PageRequestDTO;
import com.cms.mini_board.dto.PageDTO.PageResultDTO;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.service.BoardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.javassist.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Log4j2
public class BoardController {

    private final BoardService boardService;

    @GetMapping("")
    //need to change to return response entity
    public PageResultDTO<BoardPageDTO, Post> boardList(@RequestParam String pageNum) {
        PageRequestDTO req = PageRequestDTO.builder()
                .offset(Integer.valueOf(pageNum))
                .size(10).build();
        PageResultDTO<BoardPageDTO, Post> dto = boardService.getList(req);
        return dto;
    }

    //boardRead
    @GetMapping("/{postId}")
    public ResponseEntity<BoardReadDTO> boardRead(@PathVariable String postId) throws NotFoundException {
        BoardReadDTO dto = boardService.getFullBoardReadContent(postId);
        return new ResponseEntity<BoardReadDTO>(dto, HttpStatus.OK);
    }


}
