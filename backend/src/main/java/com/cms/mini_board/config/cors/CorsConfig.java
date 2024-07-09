package com.cms.mini_board.config.cors;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Log4j2
public class CorsConfig implements WebMvcConfigurer {
    @Value("${s3-endpoint}")
    private String endpoint;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        String uri = String.format("http://%s:5173", endpoint);
        String uri = String.format("http://%s", endpoint);
        log.info(uri);

        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
//                .allowedOrigins("http://127.0.0.1:5173")
//                .allowedOrigins("*")
                .allowedOrigins(uri)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Set-Cookie");

        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:5173")
//                .allowedOrigins("*")
                .allowedOrigins(uri)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Set-Cookie");
    }
}
