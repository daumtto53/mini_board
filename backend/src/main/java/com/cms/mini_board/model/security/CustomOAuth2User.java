package com.cms.mini_board.model.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class CustomOAuth2User implements OAuth2User {

//    원래는 Map 사용하는것이 맞는것.
//    private Map<String, Object> attributes;
    private final OAuth2UserDTO oAuth2UserDTO;

    public CustomOAuth2User(OAuth2UserDTO userDTO) {
        this.oAuth2UserDTO = userDTO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        List<String> roles = oAuth2UserDTO.getRoles();
        Collection<GrantedAuthority> collect = roles.stream().map(role -> new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role;
            }
        }).collect(Collectors.toList());
        authorities.addAll(collect);
        return authorities;
    }

    @Override
    public String getName() {
        return oAuth2UserDTO.getName();
    }

    public String getEmail() {
        return oAuth2UserDTO.getEmail();
    }

    public String getUsername() {
        return oAuth2UserDTO.getUsername();
    }


}
