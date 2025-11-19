package com.jcaa.hexagonal.core.port;

import java.util.UUID;

public interface TokenIssuerPort {
    String generateToken(UUID userId, String userName, String role);
    boolean validateToken(String token);
    UUID getUserIdFromToken(String token);
    String getUserNameFromToken(String token);
    String getRoleFromToken(String token);
    long getExpirationTimeFromToken(String token);
    
    /**
     * Obtiene el identificador del usuario como cadena de texto,
     * o null si el token no es válido.
     */
    default String getUserIdAsString(String token) {
        UUID userId = getUserIdFromToken(token);
        return userId != null ? userId.toString() : null;
    }
}

