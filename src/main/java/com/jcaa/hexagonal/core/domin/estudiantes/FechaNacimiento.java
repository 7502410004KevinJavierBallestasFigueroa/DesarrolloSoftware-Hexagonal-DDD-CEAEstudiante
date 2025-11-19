package com.jcaa.hexagonal.core.domin.estudiantes;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class FechaNacimiento extends ValueObject {
    
    private static final int EDAD_MINIMA = 15;
    
    private final LocalDate value;
    
    public FechaNacimiento(LocalDate value) {
        this.value = validate(value);
    }
    
    private static LocalDate validate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }
        LocalDate today = LocalDate.now();
        if (date.isAfter(today)) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }
        int edad = Period.between(date, today).getYears();
        if (edad < EDAD_MINIMA) {
            throw new IllegalArgumentException("El estudiante debe tener al menos " + EDAD_MINIMA + " años");
        }
        return date;
    }
    
    public LocalDate getValue() {
        return value;
    }
    
    public int getEdad() {
        return Period.between(value, LocalDate.now()).getYears();
    }
    
    @Override
    protected List<Object> getEqualityComponents() {
        return List.of(value);
    }
}

