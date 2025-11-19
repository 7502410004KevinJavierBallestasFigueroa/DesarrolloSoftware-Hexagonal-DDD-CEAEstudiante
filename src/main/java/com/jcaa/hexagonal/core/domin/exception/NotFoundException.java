package com.jcaa.hexagonal.core.domin.exception;

public class NotFoundException extends DomainException {
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String resource, Object id) {
        super(String.format("%s con id %s no encontrado", resource, id));
    }
}

