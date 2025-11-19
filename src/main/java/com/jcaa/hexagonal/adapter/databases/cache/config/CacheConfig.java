package com.jcaa.hexagonal.adapter.databases.cache.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    
    @Bean
    public Cache<String, Long> tokenBlacklistCache() {
        return Caffeine.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build();
    }
    
    @Bean
    public Cache<String, CacheEntry> passwordResetTokenCache() {
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
    }
    
    public static class CacheEntry {
        private final String userId;
        private final long expirationTime;
        
        public CacheEntry(String userId, long expirationTime) {
            this.userId = userId;
            this.expirationTime = expirationTime;
        }
        
        public String getUserId() {
            return userId;
        }
        
        public long getExpirationTime() {
            return expirationTime;
        }
    }
}

