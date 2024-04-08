package com.cms.mini_board.repository;

import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.Post;
import com.cms.mini_board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class ReplyRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member1 = memberRepository.findById(Long.valueOf(i)).get();
            Post post1 = postRepository.findById(Long.valueOf(i)).get();
            Reply reply = Reply.builder()
                    .post(post1)
                    .member(member1)
                    .reply_text("reply")
                    .build();
            Reply save = replyRepository.save(reply);
        });
    }

}