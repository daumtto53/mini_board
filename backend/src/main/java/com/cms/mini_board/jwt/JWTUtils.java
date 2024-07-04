package com.cms.mini_board.jwt;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JWTUtils {
    private SecretKey secretKey;

    public JWTUtils(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey)
                .build().
                parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public List<String> getRole(String token) {
        return Jwts.parser().verifyWith(secretKey)
                .build().
                parseSignedClaims(token).getPayload()
                .get("role", List.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(token).getPayload()
                .getExpiration().before(new Date());
    }


    public String createJWT(String username, List<String> roles, Long expiredMs) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
