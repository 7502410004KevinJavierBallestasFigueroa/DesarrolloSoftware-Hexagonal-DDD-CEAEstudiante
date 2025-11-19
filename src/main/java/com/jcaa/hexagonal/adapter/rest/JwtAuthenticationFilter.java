package com.jcaa.hexagonal.adapter.rest;

import com.jcaa.hexagonal.core.port.TokenBlacklistStore;
import com.jcaa.hexagonal.core.port.TokenIssuerPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final TokenIssuerPort tokenIssuer;
    private final TokenBlacklistStore tokenBlacklistStore;
    
    public JwtAuthenticationFilter(TokenIssuerPort tokenIssuer, TokenBlacklistStore tokenBlacklistStore) {
        this.tokenIssuer = tokenIssuer;
        this.tokenBlacklistStore = tokenBlacklistStore;
    }
    
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        
        if (!hasBearerToken(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = extractToken(authHeader);
        
        // Verificar si el token está en la blacklist
        if (tokenBlacklistStore.isTokenBlacklisted(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Validar token
        if (tokenIssuer.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UUID userId = tokenIssuer.getUserIdFromToken(token);
            String userName = tokenIssuer.getUserNameFromToken(token);
            String role = tokenIssuer.getRoleFromToken(token);
            
            if (userId != null && userName != null && role != null) {
                // Mapear roles: "Admin" -> "ROLE_ADMIN", "User" -> "ROLE_USER"
                String authority = "ROLE_" + role.toUpperCase();
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId.toString(),
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority(authority))
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        filterChain.doFilter(request, response);
    }
    
    private boolean hasBearerToken(String header) {
        return header != null && header.startsWith("Bearer ");
    }
    
    private String extractToken(String header) {
        return header.substring(7);
    }
}
