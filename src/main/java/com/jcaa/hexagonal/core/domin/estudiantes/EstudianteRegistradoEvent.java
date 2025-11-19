package com.jcaa.hexagonal.core.domin.estudiantes;

import com.jcaa.hexagonal.core.domin.DomainEvent;

import java.time.LocalDateTime;

public class EstudianteRegistradoEvent implements DomainEvent {
    
    private final EstudianteId estudianteId;
    private final Nombre nombre;
    private final Apellido apellido;
    private final EstudianteEmail email;
    private final LocalDateTime occurredOn;
    
    public EstudianteRegistradoEvent(EstudianteId estudianteId,
                                     Nombre nombre,
                                     Apellido apellido,
                                     EstudianteEmail email) {
        this(estudianteId, nombre, apellido, email, LocalDateTime.now());
    }
    
    public EstudianteRegistradoEvent(EstudianteId estudianteId,
                                     Nombre nombre,
                                     Apellido apellido,
                                     EstudianteEmail email,
                                     LocalDateTime occurredOn) {
        this.estudianteId = estudianteId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.occurredOn = occurredOn;
    }
    
    public EstudianteId getEstudianteId() {
        return estudianteId;
    }
    
    public Nombre getNombre() {
        return nombre;
    }
    
    public Apellido getApellido() {
        return apellido;
    }
    
    public EstudianteEmail getEmail() {
        return email;
    }
    
    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }
}

