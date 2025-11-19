package com.jcaa.hexagonal.core.port;

public interface TokenBlacklistStore {
    void addToken(String token, long expirationTimeMillis);
    boolean isTokenBlacklisted(String token);
    void removeToken(String token);
    
    /**
     * Atajo para verificar de forma positiva si un token
     * sigue siendo válido (no se encuentra en la blacklist).
     */
    default boolean isTokenAllowed(String token) {
        return !isTokenBlacklisted(token);
    }
}

