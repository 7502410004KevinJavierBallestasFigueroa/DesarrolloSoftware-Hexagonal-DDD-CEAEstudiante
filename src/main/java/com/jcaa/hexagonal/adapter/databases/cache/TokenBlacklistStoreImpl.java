package com.jcaa.hexagonal.adapter.databases.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.jcaa.hexagonal.adapter.databases.cache.config.CacheConfig;
import com.jcaa.hexagonal.core.port.TokenBlacklistStore;
import org.springframework.stereotype.Component;

@Component
public class TokenBlacklistStoreImpl implements TokenBlacklistStore {
    
    private final Cache<String, Long> cache;
    
    public TokenBlacklistStoreImpl(Cache<String, Long> tokenBlacklistCache) {
        this.cache = tokenBlacklistCache;
    }
    
    @Override
    public void addToken(String token, long expirationTimeMillis) {
        cache.put(token, expirationTimeMillis);
    }
    
    @Override
    public boolean isTokenBlacklisted(String token) {
        return cache.getIfPresent(token) != null;
    }
    
    @Override
    public void removeToken(String token) {
        cache.invalidate(token);
    }
}

