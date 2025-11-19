package com.jcaa.hexagonal.adapter.databases.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.jcaa.hexagonal.adapter.databases.cache.config.CacheConfig;
import com.jcaa.hexagonal.core.port.PasswordResetTokenStore;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PasswordResetTokenStoreImpl implements PasswordResetTokenStore {
    
    private final Cache<String, CacheConfig.CacheEntry> cache;
    
    public PasswordResetTokenStoreImpl(Cache<String, CacheConfig.CacheEntry> passwordResetTokenCache) {
        this.cache = passwordResetTokenCache;
    }
    
    @Override
    public void saveResetToken(UUID userId, String token, long expirationTimeMillis) {
        CacheConfig.CacheEntry entry = new CacheConfig.CacheEntry(
                userId.toString(),
                expirationTimeMillis
        );
        cache.put(token, entry);
    }
    
    @Override
    public UUID getUserIdByToken(String token) {
        CacheConfig.CacheEntry entry = cache.getIfPresent(token);
        if (entry == null) {
            return null;
        }
        
        // Verificar si el token ha expirado
        if (System.currentTimeMillis() > entry.getExpirationTime()) {
            cache.invalidate(token);
            return null;
        }
        
        return UUID.fromString(entry.getUserId());
    }
    
    @Override
    public void deleteToken(String token) {
        cache.invalidate(token);
    }
    
    @Override
    public boolean isValidToken(String token) {
        CacheConfig.CacheEntry entry = cache.getIfPresent(token);
        if (entry == null) {
            return false;
        }
        
        // Verificar si el token ha expirado
        if (System.currentTimeMillis() > entry.getExpirationTime()) {
            cache.invalidate(token);
            return false;
        }
        
        return true;
    }
}

