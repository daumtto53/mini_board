package com.cms.mini_board.repository;

import com.cms.mini_board.entity.Enum.Gender;
import com.cms.mini_board.entity.Member;
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
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .name("user" + i)
                    .gender(Gender.MALE)
                    .login_id("login_id" + i)
                    .nickname("nickname" + i)
                    .password("123")
                    .build();

            memberRepository.save(member);
        });
    }


}