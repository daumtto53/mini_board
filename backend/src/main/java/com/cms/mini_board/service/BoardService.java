package com.cms.mini_board.service;

import com.cms.mini_board.dto.BoardPageDTO;
import com.cms.mini_board.dto.PageDTO.PageRequestDTO;
import com.cms.mini_board.dto.PageDTO.PageResultDTO;
import com.cms.mini_board.entity.Post;

/* Repository의 Data를 가공해서 Controller로 전달하는 것이 service의 역할.*/
public interface BoardService {

    PageResultDTO<BoardPageDTO, Post> getList(PageRequestDTO requestDTO);

    default BoardPageDTO boardPageToDTO(Post post) {
        BoardPageDTO dto = BoardPageDTO.builder()
                .id(post.getPostId())
                .author(post.getMember().getName())
                .title(post.getTitle())
                .updatedAt(post.getUpdatedAt())
                .views(post.getViews())
                .replyCount(post.getReplies().size())
                .build();
        return dto;
    }
}
