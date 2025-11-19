package com.jcaa.hexagonal.core.port;

public interface PasswordHasherPort {
    
    String hash(String password);
    
    boolean verify(String password, String hash);
    
    /**
     * Alias semántico de {@link #verify(String, String)} para una lectura
     * más fluida en algunos contextos.
     */
    default boolean matches(String rawPassword, String encodedPassword) {
        return verify(rawPassword, encodedPassword);
    }
}

