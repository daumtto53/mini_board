package com.cms.mini_board.service;

import com.cms.mini_board.dto.BoardPageDTO;
import com.cms.mini_board.dto.PageDTO.PageRequestDTO;
import com.cms.mini_board.dto.PageDTO.PageResultDTO;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final PostRepository postRepository;
    @Override
    public PageResultDTO<BoardPageDTO, Post> getList(PageRequestDTO requestDTO) {
        Pageable request = requestDTO.getPageRequest(Sort.by(Sort.Direction.DESC, "postId"));
        Page<Post> result = postRepository.findAll(request);
        Function<Post, BoardPageDTO> fn = (en -> boardPageToDTO(en));
        return new PageResultDTO<>(result, fn);
    }

}
