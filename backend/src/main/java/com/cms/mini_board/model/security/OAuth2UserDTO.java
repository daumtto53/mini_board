package com.cms.mini_board.model.security;

import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuth2UserDTO {
    private List<String> roles;
    private String username;
    private String email;
    private String name;
}
