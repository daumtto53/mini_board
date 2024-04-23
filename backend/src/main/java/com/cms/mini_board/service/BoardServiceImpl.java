package com.cms.mini_board.service;

import com.cms.mini_board.dto.BoardPageDTO;
import com.cms.mini_board.dto.BoardReadDTO;
import com.cms.mini_board.dto.BoardReadReplyDTO;
import com.cms.mini_board.dto.PageDTO.PageRequestDTO;
import com.cms.mini_board.dto.PageDTO.PageResultDTO;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.entity.Reply;
import com.cms.mini_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final PostRepository postRepository;

    private static List<BoardReadReplyDTO> convertReplyToBoardReplyDTO(Post post) {
        return (List<BoardReadReplyDTO>) post.getReplies().stream()
                .map(BoardServiceImpl::createBoardReadReplyDTO)
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
    public Optional<BoardReadDTO> getFullBoardReadContent(String pageId) {
        Optional<Post> opt = postRepository.findById(Long.valueOf(pageId));
        //If optional's Content is NUll, then No Id valid. How to process this?

        //Replies 전체를 가져오면 reply와 관련된 모든 data들을 다 가져오게도미. Member 객체까지도. 모두... RepliesDTO가 필요하다.
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
            return Optional.of(dto);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Information Not Found"));
    }
}
