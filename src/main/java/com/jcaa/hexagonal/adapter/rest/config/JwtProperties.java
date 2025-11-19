package com.jcaa.hexagonal.adapter.rest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secret = "change-me-please-use-a-strong-secret-key-in-production-minimum-256-bits";
    private int accessTokenExpMinutes = 60;
    private String issuer = "hexagonal-ddd";
    private String audience = "hexagonal-ddd-client";
    
    public long accessTokenExpSeconds() {
        return accessTokenExpMinutes * 60L;
    }
}
