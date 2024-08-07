package com.cms.mini_board.service;

import com.cms.mini_board.dto.ReplyDTO;
import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.entity.Reply;
import com.cms.mini_board.model.security.CustomOAuth2User;
import com.cms.mini_board.repository.MemberRepository;
import com.cms.mini_board.repository.PostRepository;
import com.cms.mini_board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<ReplyDTO> getReplyList(Long postId) {
        Post postResult = postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No such element exception"));
        List<ReplyDTO> replyDTOS = postResult.getReplies().stream().map(reply -> replyEntityToDTO(reply)).collect(Collectors.toList());
        return replyDTOS;
    }

    @Override
    public List<ReplyDTO> getReplyListVer2(Long postId) {
        List<Reply> repliesByPostId = replyRepository.findRepliesByPostId(postId, Sort.by(Sort.Direction.ASC, "updatedAt"));
        List<ReplyDTO> replyDTOS = repliesByPostId.stream().map(reply -> replyEntityToDTO(reply)).collect(Collectors.toList());
        return replyDTOS;
    }

    @Override
    public List<ReplyDTO> getReplyListVer3(Long postId) {
        List<Reply> repliesByPostId = replyRepository.findRepliesByPostOrderByUpdatedAt(Post.builder().postId(postId).build());
        List<ReplyDTO> replyDTOS = repliesByPostId.stream().map(reply -> replyEntityToDTO(reply)).collect(Collectors.toList());
        return replyDTOS;
    }

    @Override
    public Long registerReply(ReplyDTO replyDTO) {
        CustomOAuth2User principal = (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = (String)(principal.getAttributes().get("username"));
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException());
        //replyDTO
        Reply reply = replyDTOToEntity(replyDTO);
        reply.setMember(member);

        //insert Member or post


        Reply result = replyRepository.save(reply);
        log.info("registerReply={}",result);
        return result.getReplyId();
    }

//    private ReplyDTO buildReplyDTOForRegisterReply(ReplyDTO replyDTO) {
//
//    }

//    @Override
//    public Long findReply(Long postId, Long replyId) {
//        return null;
//    }

    @Override
    @Transactional
    public Long modifyReply(ReplyDTO replyDTO) {
        Member member = memberRepository.findById(replyDTO.getMemberId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "member not found"));
        Post post = postRepository.findById(replyDTO.getPostId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "post Not found "));
        Reply persistReply = replyRepository.getReferenceById(replyDTO.getReplyId());
        persistReply.setReplyText(replyDTO.getReplyText());
        return persistReply.getReplyId();
    }

    @Override
    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }

    public boolean isAuthor(Long replyId, String username) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new NoSuchElementException());
        return reply.getMember().getUsername().equals(username);
    }
}
