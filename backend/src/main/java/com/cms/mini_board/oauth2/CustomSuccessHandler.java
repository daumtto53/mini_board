package com.cms.mini_board.oauth2;

import com.cms.mini_board.jwt.JWTUtils;
import com.cms.mini_board.model.security.CustomOAuth2User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtils jwtUtils;

    @Value("${s3-endpoint}")
    private String endpoint;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String uri = String.format("http://%s", endpoint);
        log.info(uri);

        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String username = customOAuth2User.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream().map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());
        String token = jwtUtils.createJWT(username, roles, 60*60*60L);
        log.info("onAuthenticationSuccess = JWT_TOKEN={}", token);
        log.info("onAuthenticationSuccess = USEREMAIL={}", customOAuth2User.getEmail());
        response.addCookie(createCookie("Authorization", token));
//        response.sendRedirect("http://localhost:5173");
        response.sendRedirect(uri);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
