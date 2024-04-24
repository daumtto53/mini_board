package com.cms.mini_board.service;

import com.cms.mini_board.config.MariaDBTestProfile;
import com.cms.mini_board.dto.ReplyDTO;
import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.entity.Reply;
import com.cms.mini_board.repository.MemberRepository;
import com.cms.mini_board.repository.ReplyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ReplyServiceImplTest {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    void getReplyList() {
        //given
        Long postId = 1L;

        //when

        //then
        List<ReplyDTO> replyList = replyService.getReplyList(postId);
        System.out.println(replyList);
    }

    @Test
    void getReplyListVer3() {
        //given
        Long postId = 1L;
        //when
        //then
        List<ReplyDTO> replyDTOS = replyService.getReplyListVer3(postId);
        System.out.println(replyDTOS);
    }

    @Test
    void registerReply() {
        //given
        Member member = memberRepository.findById(1L).get();
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyId(300L)
                .memberId(1L)
                .postId(1L)
                .replyAuthor(member.getNickname())
                .replyText("registerReplyTest")
                .build();
        //when
        Long l = replyService.registerReply(replyDTO);
        //then
        Reply reply = replyRepository.findById(l).get();
        assertThat(reply.getReplyText()).isEqualTo("registerReplyTest");
        assertThat(reply.getMember().getMemberId()).isEqualTo(1L);
        assertThat(reply.getMember().getNickname()).isEqualTo(member.getNickname());
        assertThat(reply.getPost().getPostId()).isEqualTo(1L);
    }

    @Test
    void modifyReply() {

        //given

        Long modifyingReplyId = 1L;
        Member member = memberRepository.findById(1L).get();
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyId(modifyingReplyId)
                .memberId(1L)
                .postId(1L)
                .replyText("ModifyTest1")
                .replyAuthor(member.getNickname())
                .build();
        Long l1 = replyService.registerReply(replyDTO);
        //when
        ReplyDTO replyDTO_changed = ReplyDTO.builder()
                .replyId(modifyingReplyId)
                .memberId(1L)
                .postId(1L)
                .replyText("ModifyTest2")
                .replyAuthor(member.getNickname())
                .build();
        Long l2 = replyService.modifyReply(replyDTO_changed);
        //then
        Reply reply = replyRepository.findById(l2).get();
        assertThat(l1).isEqualTo(l2);
        assertThat(reply.getReplyText()).isEqualTo("ModifyTest2");
    }

    @Test
    void deleteReply() {
        //given
        //when
        replyService.deleteReply(1L);
        //then
        assertThrows(NoSuchElementException.class, () -> replyRepository.findById(1L).get());
    }
}