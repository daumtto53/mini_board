package com.cms.mini_board.service;

import com.cms.mini_board.dto.ReplyDTO;
import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.entity.Reply;

import java.util.List;

public interface ReplyService {
    List<ReplyDTO> getReplyList(Long postId);

    Long registerReply(ReplyDTO replyDTO);

//    Long findReply(Long postId, Long replyId);

    Long modifyReply(ReplyDTO replyDTO);

    void deleteReply(Long replyId);

    public List<ReplyDTO> getReplyListVer2(Long postId);

    public List<ReplyDTO> getReplyListVer3(Long postId);


    //  지금은 member가 reply를 달 수 없는 상태 ( spring security 구현 안한 상태 ) 이기에
    //  front 에서 reply를 가져갈 때, 특정 member(memberId == 1)만 reply를 하도록 한다.
    default ReplyDTO replyEntityToDTO(Reply reply) {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyId(reply.getReplyId())
                .replyAuthor(reply.getMember().getNickname())
                .replyText(reply.getReplyText())
                .memberId(reply.getMember().getMemberId())
                .replyUpdatedAt(reply.getUpdatedAt())
                .build();
        return replyDTO;
    }

    //  지금은 member가 reply를 달 수 없는 상태 ( spring security 구현 안한 상태 ) 이기에
    // front에서 reply를 줄 때, 특정 member(memberId == 1)만 replyDTO를 주기로 한다.
    /** 의문
     * 의문인건, Post, Member를 build하고 있는데, 완전하지 않은 객체를 REply에 집어넣고 있잖아.
     * 그러면 이를 추후 insert, update 등을 할 때, 저절로 Member 객체나 Post 객체와 연결이 되는가?
     */
    default Reply replyDTOToEntity(ReplyDTO replyDTO) {
        Post post = Post.builder().postId(replyDTO.getPostId()).build();
        Member member = Member.builder().memberId(replyDTO.getMemberId()).build();

        Reply reply = Reply.builder()
                .replyId(replyDTO.getReplyId())
                .replyText(replyDTO.getReplyText())
                .member(member)
                .post(post)
                .build();
        return reply;
    }

    public boolean isAuthor(Long replyId, String username);
}
