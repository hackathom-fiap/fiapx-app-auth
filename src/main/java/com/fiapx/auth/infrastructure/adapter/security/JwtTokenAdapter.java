package com.fiapx.auth.infrastructure.adapter.security;

import com.fiapx.auth.domain.entity.User;
import com.fiapx.auth.domain.service.TokenServicePort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenAdapter implements TokenServicePort {

    @Value("${jwt.secret:hackathon-secret-key-fiap-x-soat11-java}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private long expiration;

    @Override
    public String generateToken(User user) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
