package com.cms.mini_board.dto.authDTO;

import lombok.Getter;

import java.util.Map;

@Getter
public class NaverResponseDTO implements OAuth2Response {

    //provider란?
    private String provider;
    //providerId란?
    private String providerId;
    private String email;
    private String name;

    public NaverResponseDTO(Map<String, Object> attribute) {
        Map<String, Object> response = (Map<String, Object>) attribute.get("response");

        this.provider = "naver";
        this.providerId = response.get("id").toString();
        this.email = response.get("email").toString();
        this.name = response.get("name").toString();
    }
}
