package com.cms.mini_board.config.security;

import com.cms.mini_board.jwt.JWTUtils;
import com.cms.mini_board.model.security.CustomOAuth2User;
import com.cms.mini_board.model.security.OAuth2UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;
        String authorizationToken = extractAuthorizationToken(request);
        if (authorizationToken == null) {
            log.info("token null");
            filterChain.doFilter(request, response);
            return ;
        }

        if (jwtUtils.isExpired(authorizationToken)) {
            log.info("isExpired");
            filterChain.doFilter(request, response);
            return ;
        }

        OAuth2UserDTO userDTO = getOAuth2UserFromJWT(authorizationToken);

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDTO, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private OAuth2UserDTO getOAuth2UserFromJWT(String authorizationToken) {
        String username = jwtUtils.getUsername(authorizationToken);
        List<String> roles = jwtUtils.getRole(authorizationToken);
        OAuth2UserDTO userDTO = OAuth2UserDTO.builder()
                .username(username)
                .roles(roles)
                .build();
        return userDTO;
    }

    private String extractAuthorizationToken(HttpServletRequest request) {
        String authorization = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }
        return authorization;
    }
}