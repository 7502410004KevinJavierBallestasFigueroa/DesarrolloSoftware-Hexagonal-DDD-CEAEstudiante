package com.jcaa.hexagonal.core.domin.estudiantes;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;

public class Semestre extends ValueObject {
    
    private static final int MIN_SEMESTRE = 1;
    private static final int MAX_SEMESTRE = 10;
    
    private final int value;
    
    public Semestre(Integer value) {
        this.value = validate(value);
    }
    
    private static int validate(Integer raw) {
        if (raw == null) {
            throw new IllegalArgumentException("El semestre no puede ser nulo");
        }
        if (raw < MIN_SEMESTRE || raw > MAX_SEMESTRE) {
            throw new IllegalArgumentException("El semestre debe estar entre " + MIN_SEMESTRE + " y " + MAX_SEMESTRE);
        }
        return raw;
    }
    
    public int getValue() {
        return value;
    }
    
    @Override
    protected List<Object> getEqualityComponents() {
        return List.of(value);
    }
}

