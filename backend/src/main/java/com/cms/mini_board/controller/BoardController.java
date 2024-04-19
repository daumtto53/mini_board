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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Log4j2
public class BoardController {

    private final BoardService boardService;

//    @GetMapping("")
//    public List<BoardPageDTO> boardPage() {
//        Page<Post> page = boardService.findBoardPageWithPagination(0, 10);
//        return page;
//    }

    @GetMapping("")
    public PageResultDTO<BoardPageDTO, Post> boardList() {
        PageRequestDTO req = PageRequestDTO.builder()
                .offset(1)
                .size(10).build();
        PageResultDTO<BoardPageDTO, Post> dto = boardService.getList(req);
        return dto;
    }

}
