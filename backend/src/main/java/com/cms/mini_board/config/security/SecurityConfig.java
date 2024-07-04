package com.cms.mini_board.config.security;

import com.cms.mini_board.jwt.JWTUtils;
import com.cms.mini_board.oauth2.CustomSuccessHandler;
import com.cms.mini_board.service.auth.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
//@EnableWebSecurity(debug = true)
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtils jwtUtils;
    private final JWTFilter jwtFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable());

        //form, basic Login 제거
        http.formLogin(c -> c.disable());
        http.httpBasic(c -> c.disable());

        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(
                new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);
                        configuration.setExposedHeaders(Collections.unmodifiableList(Arrays.asList("Set-Cookie", "Authorization")));
                        return configuration;
                    }
                }
        ));

        //JWTFilter 적용
        http.addFilterAfter(jwtFilter, OAuth2LoginAuthenticationFilter.class);
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        //OAuth2Provider가 성공했을 때, userService를 customOAuth2UserSErvice를 사용하게끔 하고,
        //userService에서 oAuth2User / principal을 반환받았을때 사용할 successHandler를 지정한다.
        //userInfo Endpoint 개념을 떠올려보자.
        http.oauth2Login(configurer -> configurer
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService))
                .successHandler(customSuccessHandler)
                .failureHandler((request, response, exception) -> {
                    customAuthenticationEntryPoint.commence(request,response,exception);
                })
        );
        http.exceptionHandling(configurer -> configurer
                .authenticationEntryPoint(customAuthenticationEntryPoint)
        );

        // root 설정에 대해서만 permit All
        // 나머지 모든 URL에 대한 request에 대해서는 authenticated를 요구한다.
        http.authorizeHttpRequests(customizer -> customizer
                        .requestMatchers("/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/board").hasRole("USER")
//                .requestMatchers("/").permitAll()
//                .anyRequest().authenticated()
    );


        //Session State를 서버에서 사용하지 않을 것임을 선언함.
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


}
