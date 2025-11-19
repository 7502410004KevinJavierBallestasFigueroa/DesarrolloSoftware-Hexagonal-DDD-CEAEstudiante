package com.jcaa.hexagonal.core.domin.estudiantes;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;
import java.util.Objects;

public class Telefono extends ValueObject {
    
    private final String value;
    
    public Telefono(String value) {
        this.value = normalizeAndValidate(value);
    }
    
    private static String normalizeAndValidate(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("El teléfono no puede ser nulo");
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (!trimmed.matches("\\d{10}")) {
            throw new IllegalArgumentException("El teléfono debe contener exactamente 10 dígitos");
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
        Telefono telefono = (Telefono) obj;
        return Objects.equals(value, telefono.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

