package com.jcaa.hexagonal.adapter.rest;

import com.jcaa.hexagonal.adapter.rest.config.JwtProperties;
import com.jcaa.hexagonal.core.port.TokenIssuerPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenIssuerAdapter implements TokenIssuerPort {
    
    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;
    
    public JwtTokenIssuerAdapter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
    
    @Override
    public String generateToken(UUID userId, String userName, String role) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getAccessTokenExpMinutes() * 60L);
        
        return Jwts.builder()
                .subject(userId.toString())
                .claim("userName", userName)
                .claim("role", role)
                .issuer(jwtProperties.getIssuer())
                .audience().add(jwtProperties.getAudience()).and()
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
    }
    
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .requireIssuer(jwtProperties.getIssuer())
                    .requireAudience(jwtProperties.getAudience())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    @Override
    public UUID getUserIdFromToken(String token) {
        try {
            Claims claims = parser()
                    .parseSignedClaims(token)
                    .getPayload();
            return UUID.fromString(claims.getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
    
    @Override
    public String getUserNameFromToken(String token) {
        try {
            Claims claims = parser()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("userName", String.class);
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
    
    @Override
    public String getRoleFromToken(String token) {
        try {
            Claims claims = parser()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("role", String.class);
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
    
    @Override
    public long getExpirationTimeFromToken(String token) {
        try {
            Claims claims = parser()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getExpiration().getTime();
        } catch (JwtException | IllegalArgumentException e) {
            return 0;
        }
    }
    
    private JwtParser parser() {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build();
    }
}
