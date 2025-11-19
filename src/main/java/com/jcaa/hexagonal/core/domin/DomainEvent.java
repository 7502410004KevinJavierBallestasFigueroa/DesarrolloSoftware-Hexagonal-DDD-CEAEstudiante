package com.jcaa.hexagonal.core.domin;

import java.time.LocalDateTime;

public interface DomainEvent {
    
    /**
     * Momento en el que ocurrió el evento.
     */
    LocalDateTime occurredOn();
    
    /**
     * Utilidad para comparar tipos de eventos sin necesidad
     * de exponer la clase concreta en otros lugares del dominio.
     */
    default boolean isSameType(DomainEvent other) {
        return other != null && getClass().equals(other.getClass());
    }
}

