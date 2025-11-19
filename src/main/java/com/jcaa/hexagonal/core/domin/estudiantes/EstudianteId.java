package com.jcaa.hexagonal.core.domin.estudiantes;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;
import java.util.UUID;

public class EstudianteId extends ValueObject {
    
    private static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    
    private final UUID value;
    
    public EstudianteId(UUID value) {
        this.value = requireValid(value);
    }
    
    private static UUID requireValid(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("El ID del estudiante no puede ser nulo");
        }
        if (EMPTY_UUID.equals(value)) {
            throw new IllegalArgumentException("El ID del estudiante no puede estar vacío");
        }
        return value;
    }
    
    public static EstudianteId newId() {
        return new EstudianteId(UUID.randomUUID());
    }
    
    public static EstudianteId from(UUID value) {
        return new EstudianteId(value);
    }
    
    public UUID getValue() {
        return value;
    }
    
    @Override
    protected List<Object> getEqualityComponents() {
        return List.of(value);
    }
}

