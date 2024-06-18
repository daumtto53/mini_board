package com.cms.mini_board.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OAuth2Config {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

}
