package com.cms.mini_board.repository;

import com.cms.mini_board.entity.Enum.Gender;
import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class PostRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    public void insertMembers() {
        //given
        IntStream.rangeClosed(1,100).forEach(i -> {
            Optional<Member> byId = memberRepository.findById(Long.valueOf(i));
            Member member = byId.get();
            Post post1 = Post.builder()
                    .member(member)
                    .title("title " + i + " 1")
                    .views(Long.valueOf(i))
                    .content("content" + String.valueOf(i) + " 1")
                    .build();

            Post post2 = Post.builder()
                    .member(member)
                    .title("title " + i + " 2")
                    .views(Long.valueOf(i))
                    .content("content" + String.valueOf(i) + " 2")
                    .build();
            postRepository.save(post1);
            postRepository.save(post2);
        });
    }



}