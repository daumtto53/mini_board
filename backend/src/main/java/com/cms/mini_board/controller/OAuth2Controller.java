package com.cms.mini_board.controller;

import com.cms.mini_board.config.OAuth2Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2Config oAuth2Config;

    @PostMapping("/login/oauth2/authorization/google")
    public ResponseEntity<String> login(@RequestHeader Map<String, String> headers) {

        String authKV = headers.get("Authentication");
        log.info("authKV={}", authKV);
        return new ResponseEntity<>("123123", HttpStatus.OK);

    }
}
