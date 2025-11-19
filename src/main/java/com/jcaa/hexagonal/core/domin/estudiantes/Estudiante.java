package com.jcaa.hexagonal.core.domin.estudiantes;

import com.jcaa.hexagonal.core.domin.AggregateRoot;

import java.time.LocalDateTime;
import java.util.Objects;

public class Estudiante extends AggregateRoot {
    
    private EstudianteId id;
    private Nombre nombre;
    private Apellido apellido;
    private FechaNacimiento fechaNacimiento;
    private Semestre semestre;
    private EstudianteEmail email;
    private Genero genero;
    private Telefono telefono;
    private Programa programa;
    private Universidad universidad;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private Estudiante() {
        // Constructor privado para reconstitución desde la base de datos
    }
    
    private Estudiante(EstudianteId id,
                       Nombre nombre,
                       Apellido apellido,
                       FechaNacimiento fechaNacimiento,
                       Semestre semestre,
                       EstudianteEmail email,
                       Genero genero,
                       Telefono telefono,
                       Programa programa,
                       Universidad universidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.semestre = semestre;
        this.email = email;
        this.genero = genero;
        this.telefono = telefono;
        this.programa = programa;
        this.universidad = universidad;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
        
        addDomainEvent(new EstudianteRegistradoEvent(id, nombre, apellido, email));
    }
    
    public static Estudiante registrar(Nombre nombre,
                                       Apellido apellido,
                                       FechaNacimiento fechaNacimiento,
                                       Semestre semestre,
                                       EstudianteEmail email,
                                       Genero genero,
                                       Telefono telefono,
                                       Programa programa,
                                       Universidad universidad) {
        return new Estudiante(EstudianteId.newId(), nombre, apellido, fechaNacimiento, semestre,
                email, genero, telefono, programa, universidad);
    }
    
    public static Estudiante reconstitute(EstudianteId id,
                                          Nombre nombre,
                                          Apellido apellido,
                                          FechaNacimiento fechaNacimiento,
                                          Semestre semestre,
                                          EstudianteEmail email,
                                          Genero genero,
                                          Telefono telefono,
                                          Programa programa,
                                          Universidad universidad,
                                          LocalDateTime createdAt,
                                          LocalDateTime updatedAt) {
        Estudiante estudiante = new Estudiante();
        estudiante.id = id;
        estudiante.nombre = nombre;
        estudiante.apellido = apellido;
        estudiante.fechaNacimiento = fechaNacimiento;
        estudiante.semestre = semestre;
        estudiante.email = email;
        estudiante.genero = genero;
        estudiante.telefono = telefono;
        estudiante.programa = programa;
        estudiante.universidad = universidad;
        estudiante.createdAt = createdAt;
        estudiante.updatedAt = updatedAt;
        return estudiante;
    }
    
    private void markUpdated() {
        this.updatedAt = LocalDateTime.now();
    }
    
    public void actualizarNombre(Nombre nombre) {
        if (this.nombre.equals(nombre)) {
            return;
        }
        this.nombre = nombre;
        markUpdated();
    }
    
    public void actualizarApellido(Apellido apellido) {
        if (this.apellido.equals(apellido)) {
            return;
        }
        this.apellido = apellido;
        markUpdated();
    }
    
    public void actualizarFechaNacimiento(FechaNacimiento fechaNacimiento) {
        if (this.fechaNacimiento.equals(fechaNacimiento)) {
            return;
        }
        this.fechaNacimiento = fechaNacimiento;
        markUpdated();
    }
    
    public void actualizarSemestre(Semestre semestre) {
        if (this.semestre.equals(semestre)) {
            return;
        }
        this.semestre = semestre;
        markUpdated();
    }
    
    public void actualizarEmail(EstudianteEmail email) {
        if (this.email.equals(email)) {
            return;
        }
        this.email = email;
        markUpdated();
    }
    
    public void actualizarGenero(Genero genero) {
        if (this.genero.equals(genero)) {
            return;
        }
        this.genero = genero;
        markUpdated();
    }
    
    public void actualizarTelefono(Telefono telefono) {
        if (this.telefono.equals(telefono)) {
            return;
        }
        this.telefono = telefono;
        markUpdated();
    }
    
    public void actualizarPrograma(Programa programa) {
        if (this.programa.equals(programa)) {
            return;
        }
        this.programa = programa;
        markUpdated();
    }
    
    public void actualizarUniversidad(Universidad universidad) {
        if (this.universidad.equals(universidad)) {
            return;
        }
        this.universidad = universidad;
        markUpdated();
    }
    
    public EstudianteId getId() {
        return id;
    }
    
    public Nombre getNombre() {
        return nombre;
    }
    
    public Apellido getApellido() {
        return apellido;
    }
    
    public FechaNacimiento getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public Semestre getSemestre() {
        return semestre;
    }
    
    public EstudianteEmail getEmail() {
        return email;
    }
    
    public Genero getGenero() {
        return genero;
    }
    
    public Telefono getTelefono() {
        return telefono;
    }
    
    public Programa getPrograma() {
        return programa;
    }
    
    public Universidad getUniversidad() {
        return universidad;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estudiante that = (Estudiante) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

