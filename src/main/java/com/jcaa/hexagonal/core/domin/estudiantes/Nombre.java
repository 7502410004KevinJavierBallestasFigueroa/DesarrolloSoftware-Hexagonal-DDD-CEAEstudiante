package com.jcaa.hexagonal.core.domin.estudiantes;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Nombre extends ValueObject {
    
    private static final Pattern VALID_PATTERN = Pattern.compile("^[A-Za-z ]+$");
    
    private final String value;
    
    public Nombre(String value) {
        this.value = normalizeAndValidate(value, "El nombre");
    }
    
    static String normalizeAndValidate(String raw, String fieldLabel) {
        if (raw == null) {
            throw new IllegalArgumentException(fieldLabel + " no puede ser nulo");
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(fieldLabel + " no puede estar vacio");
        }
        int length = trimmed.length();
        if (length < 3) {
            throw new IllegalArgumentException(fieldLabel + " debe tener al menos 3 caracteres");
        }
        if (length > 20) {
            throw new IllegalArgumentException(fieldLabel + " no puede tener mas de 20 caracteres");
        }
        if (!VALID_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException(fieldLabel + " solo puede contener letras y espacios");
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nombre nombre = (Nombre) o;
        return Objects.equals(value, nombre.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

