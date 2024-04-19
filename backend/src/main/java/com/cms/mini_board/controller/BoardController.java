package com.cms.mini_board.controller;

import com.cms.mini_board.dto.BoardPageDTO;
import com.cms.mini_board.dto.PageDTO.PageRequestDTO;
import com.cms.mini_board.dto.PageDTO.PageResultDTO;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Log4j2
public class BoardController {

    private final BoardService boardService;

    @GetMapping("")
    public PageResultDTO<BoardPageDTO, Post> boardList(@RequestParam String pageNum) {
        PageRequestDTO req = PageRequestDTO.builder()
                .offset(Integer.valueOf(pageNum))
                .size(10).build();
        PageResultDTO<BoardPageDTO, Post> dto = boardService.getList(req);
        return dto;
    }

    //전체 페이지 개수 보내기

}
