package com.jcaa.hexagonal.core.domin.estudiantes;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class EstudianteEmail extends ValueObject {
    
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.(com|co)$", Pattern.CASE_INSENSITIVE);
    
    private final String value;
    
    public EstudianteEmail(String value) {
        String normalized = normalize(value);
        if (!EMAIL_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("El email del estudiante no es válido. Debe contener '@' y terminar en .com o .co");
        }
        this.value = normalized;
    }
    
    private static String normalize(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("El email del estudiante no puede estar vacío");
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("El email del estudiante no puede estar vacío");
        }
        return trimmed.toLowerCase();
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
        EstudianteEmail email = (EstudianteEmail) obj;
        return Objects.equals(value, email.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

