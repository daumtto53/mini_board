package com.cms.mini_board.service.auth;

import com.cms.mini_board.dto.authDTO.NaverResponseDTO;
import com.cms.mini_board.dto.authDTO.OAuth2Response;
import com.cms.mini_board.entity.Member;
import com.cms.mini_board.entity.Role;
import com.cms.mini_board.entity.manytomany.MemberRole;
import com.cms.mini_board.model.security.CustomOAuth2User;
import com.cms.mini_board.model.security.OAuth2UserDTO;
import com.cms.mini_board.repository.MemberRepository;
import com.cms.mini_board.repository.MemberRoleRepository;
import com.cms.mini_board.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponseDTO(oAuth2User.getAttributes());
        } else {
            return null;
        }
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        Optional<Member> member = memberRepository.findByUsername(username);
        // 가입한적이 없다면 가입시킨다.
        //nickname, password, loginId는 임시로 주입.
        if (member.isEmpty()) {
            registerOAuth2ResponseToMember(username, oAuth2Response);
            OAuth2UserDTO userDTO = createUserDTO(username, oAuth2Response);
            return new CustomOAuth2User(userDTO);
        }
        else { //원래의 Service에서 user 정보가 바뀌었을수도 있으므로..
            // TODO:기타 다른 정보들도 set 필요
            member.get().setEmail(oAuth2Response.getEmail());
            member.get().setName(oAuth2Response.getName());
            memberRepository.save(member.get());
            List<String> rolesList = memberRepository.findRolesByMemberId(member.get().getMemberId())
                    .stream().map(r -> r.getName()).collect(Collectors.toList());
            OAuth2UserDTO userDTO = createuserDTO(username, member.get(), rolesList);
            return new CustomOAuth2User(userDTO);
        }
    }

    private static OAuth2UserDTO createuserDTO(String username, Member member, List<String> rolesList) {
        OAuth2UserDTO userDTO = OAuth2UserDTO.builder()
                .username(username)
                .name(member.getName())
                .email(member.getEmail())
                .roles(rolesList)
                .build();
        return userDTO;
    }

    private static OAuth2UserDTO createUserDTO(String username, OAuth2Response oAuth2Response) {
        OAuth2UserDTO userDTO = OAuth2UserDTO.builder()
                .username(username)
                .name(oAuth2Response.getName())
                .email(oAuth2Response.getEmail())
                .roles(Arrays.asList("ROLE_USER"))
                .build();
        return userDTO;
    }

    private void registerOAuth2ResponseToMember(String username, OAuth2Response oAuth2Response) {
        Member member;
        member = Member.builder()
                .username(username)
                .email(oAuth2Response.getEmail())
                .loginId(oAuth2Response.getProviderId())
                .password(username)
                .isFromSocial(true)
                .isDisabled(false)
                .name(oAuth2Response.getName())
                .nickname(oAuth2Response.getEmail())
                .build();
        memberRepository.save(member);
        //Role 추가
        registerMemberRole(member);
    }

    private void registerMemberRole(Member member) {
        Role roleUser = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new NoSuchElementException());
        MemberRole memberRole = MemberRole.builder()
                .role(roleUser)
                .member(member)
                .build();
        memberRoleRepository.save(memberRole);
    }
}
