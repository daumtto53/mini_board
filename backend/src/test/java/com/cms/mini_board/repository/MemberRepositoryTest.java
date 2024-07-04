package com.cms.mini_board.repository;

import com.cms.mini_board.config.MariaDBTestProfile;
import com.cms.mini_board.entity.Role;
import com.cms.mini_board.entity.constants.Gender;
import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.manytomany.MemberRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@SpringBootTest (classes = {
//        MariaDBTestProfile.class
//})
@SpringBootTest
@Transactional
@Commit
//@ActiveProfiles("test")
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberRoleRepository memberRoleRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void clearData() {
        memberRepository.deleteAll();
    }

    @Test
    public void insertMembers() {

        Role role= roleRepository.findByName("ROLE_USER").get();

        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .name("user" + i)
                    .username("naver testcode" + i)
                    .email(String.format("email%d@naver.com", i))
                    .loginId("login_id" + i)
                    .password("123")
                    .nickname("nickname" + i)
                    .gender(Gender.MALE)
                    .build();
            Member save = memberRepository.save(member);
            MemberRole memberRole = MemberRole.builder()
                    .role(Role.builder().roleId(role.getRoleId()).build())
                    .member(save)
                    .build();
            memberRoleRepository.save(memberRole);


            //memberRole Update

        });
    }

    @Test
    public void findMember() {
        //given
        memberRepository.deleteAll();
        Member member = Member.builder()
                .name("user")
                .gender(Gender.MALE)
                .loginId("login_id")
                .nickname("nickname")
                .password("123")
                .build();
        //when
        Member find = memberRepository.save(member);
        Assertions.assertThat(find.getName()).isEqualTo(member.getName());
    }

    @Test
    void findRoleByEmail() {
        ///given

        String email = "email50@naver.com";
        //when
        List<Role> rolesByEmail = memberRepository.findRolesByEmail(email);
        List<String> collect = rolesByEmail.stream().map(role -> role.getName()).collect(Collectors.toList());
        //then
        Assertions.assertThat(collect).containsOnly("ROLE_USER");
    }

    @Test
    public void test() {

    }

}