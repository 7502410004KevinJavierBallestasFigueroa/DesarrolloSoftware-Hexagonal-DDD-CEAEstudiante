package com.jcaa.hexagonal.core.domin.estudiantes;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;
import java.util.Objects;

public class Apellido extends ValueObject {
    
    private final String value;
    
    public Apellido(String value) {
        this.value = Nombre.normalizeAndValidate(value, "El apellido");
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
        Apellido apellido = (Apellido) o;
        return Objects.equals(value, apellido.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

