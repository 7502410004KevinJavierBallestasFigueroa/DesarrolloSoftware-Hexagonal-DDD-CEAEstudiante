package com.jcaa.hexagonal.core.domin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot {
    
    private final List<DomainEvent> domainEvents = new ArrayList<>();
    
    /**
     * Devuelve una vista inmutable de los eventos de dominio
     * actualmente registrados en el agregado.
     */
    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
    
    /**
     * Indica si el agregado tiene eventos de dominio pendientes.
     */
    public boolean hasDomainEvents() {
        return !domainEvents.isEmpty();
    }
    
    /**
     * Registra un nuevo evento de dominio. Si el evento es nulo
     * simplemente se ignora para evitar errores en tiempo de ejecución.
     */
    protected void addDomainEvent(DomainEvent domainEvent) {
        if (domainEvent == null) {
            return;
        }
        domainEvents.add(domainEvent);
    }
    
    /**
     * Elimina todos los eventos de dominio actualmente registrados.
     */
    public void clearDomainEvents() {
        domainEvents.clear();
    }
}

