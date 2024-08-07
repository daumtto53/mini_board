package com.cms.mini_board.repository;

import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
@Commit
//@ActiveProfiles("test")
class PostRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    public void insertMembers() {
        //given
        IntStream.rangeClosed(1,10).forEach(i -> {
//            Optional<Member> byId = memberRepository.findById(Long.valueOf(i));
//            Member member = byId.get();
            Member member = memberRepository.findById(1L).get();

            Post post1 = Post.builder()
                    .member(member)
                    .title("title " + i + " 1")
                    .views(Long.valueOf(i))
                    .content("content " + String.valueOf(i) + " 1")
                    .build();

            Post post2 = Post.builder()
                    .member(member)
                    .title("title " + i + " 2")
                    .views(Long.valueOf(i))
                    .content("content " + String.valueOf(i) + " 2")
                    .build();
            postRepository.save(post1);
            postRepository.save(post2);
        });
    }



}