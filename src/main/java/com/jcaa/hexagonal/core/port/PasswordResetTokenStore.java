package com.jcaa.hexagonal.core.port;

import java.util.UUID;

public interface PasswordResetTokenStore {
    void saveResetToken(UUID userId, String token, long expirationTimeMillis);
    UUID getUserIdByToken(String token);
    void deleteToken(String token);
    boolean isValidToken(String token);
    
    /**
     * Alias utilitario para invalidar un token de reseteo.
     */
    default void invalidateToken(String token) {
        deleteToken(token);
    }
}

