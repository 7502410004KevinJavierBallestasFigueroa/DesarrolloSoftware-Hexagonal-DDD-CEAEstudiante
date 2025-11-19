package com.jcaa.hexagonal.core.domin.users;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Email extends ValueObject {
    private final String value;
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    public Email(String value) {
        String normalized = normalize(value);
        if (!isValidEmail(normalized)) {
            throw new IllegalArgumentException("El formato del email no es válido");
        }
        this.value = normalized;
    }
    
    private static String normalize(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        return trimmed.toLowerCase();
    }
    
    private static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    protected List<Object> getEqualityComponents() {
        return List.of(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Email email = (Email) obj;
        return Objects.equals(value, email.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
