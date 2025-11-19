package com.jcaa.hexagonal.adapter.rest;

import com.jcaa.hexagonal.core.port.PasswordHasherPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordHasher implements PasswordHasherPort {
    
    private final PasswordEncoder passwordEncoder;
    
    public BCryptPasswordHasher(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public String hash(String password) {
        return passwordEncoder.encode(password);
    }
    
    @Override
    public boolean verify(String password, String hash) {
        return passwordEncoder.matches(password, hash);
    }
}
