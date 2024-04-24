package com.cms.mini_board.service;

import com.cms.mini_board.dto.ReplyDTO;
import com.cms.mini_board.entity.Reply;

import java.util.List;

public interface ReplyService {
    List<ReplyDTO> getReplyList(Long postId);

    Long registerReply(Long postId, ReplyDTO replyDTO);

//    Long findReply(Long postId, Long replyId);

    void modifyReply(ReplyDTO replyDTO);

    void deleteReply(Long replyId);

    //    public List<ReplyDTO> getReplyListVer2(Long postId);
    public List<ReplyDTO> getReplyListVer3(Long postId);


    default ReplyDTO replyEntityToDTO(Reply reply) {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyId(reply.getReplyId())
                .replyAuthor(reply.getMember().getNickname())
                .replyText(reply.getReplyText())
                .replyUpdatedAt(reply.getUpdatedAt())
                .build();
        return replyDTO;
    }

    default Reply replyDTOToEntity(ReplyDTO replyDTO) {
        Reply reply = Reply.builder()
                .replyId(replyDTO.getReplyId())
                .replyText(replyDTO.getReplyText())
                .build();
        return reply;
    }
}
