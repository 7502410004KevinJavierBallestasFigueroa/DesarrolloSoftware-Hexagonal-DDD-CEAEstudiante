package com.jcaa.hexagonal.core.domin.users;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;
import java.util.Objects;

public class UserName extends ValueObject {
    private final String value;
    
    public UserName(String value) {
        this.value = normalizeAndValidate(value);
    }
    
    private static String normalizeAndValidate(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }
        String trimmed = raw.trim();
        int length = trimmed.length();
        if (length < 3) {
            throw new IllegalArgumentException("El nombre de usuario debe tener al menos 3 caracteres");
        }
        if (length > 50) {
            throw new IllegalArgumentException("El nombre de usuario no puede tener más de 50 caracteres");
        }
        return trimmed;
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
        UserName userName = (UserName) obj;
        return Objects.equals(value, userName.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
