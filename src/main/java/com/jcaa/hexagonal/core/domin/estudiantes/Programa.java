package com.jcaa.hexagonal.core.domin.estudiantes;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;
import java.util.Objects;

public class Programa extends ValueObject {
    
    private final String value;
    
    public Programa(String value) {
        this.value = normalizeAndValidate(value, "El programa");
    }
    
    private static String normalizeAndValidate(String raw, String fieldLabel) {
        if (raw == null) {
            throw new IllegalArgumentException(fieldLabel + " no puede ser nulo");
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(fieldLabel + " no puede estar vacío");
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
        Programa programa = (Programa) obj;
        return Objects.equals(value, programa.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

