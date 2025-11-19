package com.jcaa.hexagonal.core.domin.users;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;
import java.util.UUID;

public class UserId extends ValueObject {
    private static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    
    private final UUID value;
    
    public UserId(UUID value) {
        this.value = requireValid(value);
    }
    
    private static UUID requireValid(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }
        if (EMPTY_UUID.equals(value)) {
            throw new IllegalArgumentException("El ID del usuario no puede estar vacío");
        }
        return value;
    }
    
    public static UserId newId() {
        return new UserId(UUID.randomUUID());
    }
    
    public static UserId from(UUID value) {
        return new UserId(value);
    }
    
    public UUID getValue() {
        return value;
    }
    
    @Override
    protected List<Object> getEqualityComponents() {
        return List.of(value);
    }
}
