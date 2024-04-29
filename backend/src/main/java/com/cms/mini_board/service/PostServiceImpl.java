package com.cms.mini_board.service;

import com.cms.mini_board.dto.BoardPageDTO;
import com.cms.mini_board.dto.BoardReadDTO;
import com.cms.mini_board.dto.BoardReadReplyDTO;
import com.cms.mini_board.dto.PageDTO.PageRequestDTO;
import com.cms.mini_board.dto.PageDTO.PageResultDTO;
import com.cms.mini_board.dto.PostDTO;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.entity.Reply;
import com.cms.mini_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private static List<BoardReadReplyDTO> convertReplyToBoardReplyDTO(Post post) {
        return (List<BoardReadReplyDTO>) post.getReplies().stream()
                .map(PostServiceImpl::createBoardReadReplyDTO)
                .collect(Collectors.toList());
    }
    private static BoardReadReplyDTO createBoardReadReplyDTO(Reply reply) {
        BoardReadReplyDTO replyDTO = BoardReadReplyDTO.builder()
                .replyId(reply.getReplyId())
                .replyAuthor(reply.getMember().getNickname())
                .replyText(reply.getReplyText())
                .replyUpdatedAt(reply.getUpdatedAt())
                .build();
        return replyDTO;
    }

    @Override
    public PageResultDTO<BoardPageDTO, Post> getList(PageRequestDTO requestDTO) {
        Pageable request = requestDTO.getPageRequest(Sort.by(Sort.Direction.DESC, "postId"));
        Page<Post> result = postRepository.findAll(request);
        Function<Post, BoardPageDTO> fn = (en -> boardPageToDTO(en));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardReadDTO getFullBoardReadContent(String postId) {
        Optional<Post> opt = postRepository.findById(Long.valueOf(postId));

        return opt.map((post) -> {
            BoardReadDTO dto = BoardReadDTO.builder()
                    .title(post.getTitle())
                    .author(post.getMember().getNickname())
                    .content(post.getContent())
                    .updatedAt(post.getUpdatedAt())
                    .views(post.getViews())
                    .boardReadReplyDTOList(
                            convertReplyToBoardReplyDTO(post)
                    )
                    .build();
            return dto;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Information Not Found"));
    }

    @Override
    public Long writePost(PostDTO postDTO) {
        Post post = postDTOToEntity(postDTO);
        Post save = postRepository.save(post);
        return save.getPostId();
    }

    @Override
    @Transactional
    public Long modifyPost(PostDTO postDTO) {
        Post post = postRepository.findById(postDTO.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "post Not Found"));
        Post toSave = postDTOToEntity(postDTO);
        Post modfiedPost = postRepository.save(toSave);
        return modfiedPost.getPostId();
    }

    //cascade 사용하지 않기.
    //query를 사용하는 방법을 바꿔야함.
    @Override
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteAllByReplyId(postId);
        postRepository.deleteById(postId);
    }
}
