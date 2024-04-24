package com.cms.mini_board.service;

import com.cms.mini_board.config.MariaDBTestProfile;
import com.cms.mini_board.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ReplyServiceImplTest {

    @Autowired
    private ReplyService replyService;
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
    void registerReply() {
    }

    @Test
    void modifyReply() {
    }

    @Test
    void deleteReply() {
    }
}