package com.cms.mini_board.service;

import com.cms.mini_board.dto.ReplyDTO;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.entity.Reply;
import com.cms.mini_board.repository.PostRepository;
import com.cms.mini_board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    @Override
    public List<ReplyDTO> getReplyList(Long postId) {
        Post postResult = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No such element exception"));
        List<ReplyDTO> replyDTOS = postResult.getReplies().stream().map(reply ->
            replyEntityToDTO(reply)
        ).collect(Collectors.toList());
        return replyDTOS;
    }

    @Override
    public List<ReplyDTO> getReplyListVer2(Long postId) {
        List<Reply> repliesByPostId = replyRepository.findRepliesByPostId(postId, Sort.by(Sort.Direction.ASC, "updatedAt"));
        List<ReplyDTO> replyDTOS = repliesByPostId.stream().map(reply ->
                replyEntityToDTO(reply)
        ).collect(Collectors.toList());
        return replyDTOS;
    }

    @Override
    public List<ReplyDTO> getReplyListVer3(Long postId) {
        List<Reply> repliesByPostId = replyRepository.findRepliesByPost(
                Post.builder().postId(postId).build()
        );
        List<ReplyDTO> replyDTOS = repliesByPostId
                .stream().map(reply -> replyEntityToDTO(reply)).collect(Collectors.toList());
        return replyDTOS;
    }

    @Override
    public Long registerReply(Long postId, ReplyDTO replyDTO) {

        return 1L;
    }

//    @Override
//    public Long findReply(Long postId, Long replyId) {
//        return null;
//    }

    @Override
    public void modifyReply(ReplyDTO replyDTO) {

    }

    @Override
    public void deleteReply(Long replyId) {
    }
}
