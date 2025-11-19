package com.jcaa.hexagonal.core.domin.users;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;
import java.util.Objects;

public class PasswordHash extends ValueObject {
    private final String value;
    
    public PasswordHash(String value) {
        this.value = validate(value);
    }
    
    private static String validate(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException("El hash de la contraseña no puede estar vacío");
        }
        return raw;
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
        PasswordHash that = (PasswordHash) obj;
        return Objects.equals(value, that.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
