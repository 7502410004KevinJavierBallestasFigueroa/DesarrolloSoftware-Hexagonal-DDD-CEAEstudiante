package com.jcaa.hexagonal.core.domin.estudiantes;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Genero extends ValueObject {
    
    private static final Set<String> VALID_GENEROS = Set.of("M", "F", "Otro");
    
    private final String value;
    
    public Genero(String value) {
        String normalized = normalize(value);
        validateGenero(normalized, value);
        this.value = normalized;
    }
    
    private static String normalize(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("El género no puede estar vacío");
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("El género no puede estar vacío");
        }
        return trimmed;
    }
    
    private static void validateGenero(String normalized, String original) {
        if (!VALID_GENEROS.contains(normalized)) {
            throw new IllegalArgumentException(
                    String.format("El género '%s' no es válido. Valores permitidos: %s",
                            original, String.join(", ", VALID_GENEROS))
            );
        }
    }
    
    public String getValue() {
        return value;
    }
    
    public boolean isMasculino() {
        return "M".equals(value);
    }
    
    public boolean isFemenino() {
        return "F".equals(value);
    }
    
    public boolean isOtro() {
        return "Otro".equals(value);
    }
    
    @Override
    protected List<Object> getEqualityComponents() {
        return List.of(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Genero genero = (Genero) obj;
        return Objects.equals(value, genero.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

