package com.jcaa.hexagonal.adapter.databases.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "estudiantes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity {
    
    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;
    
    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;
    
    @Column(name = "apellido", nullable = false, length = 20)
    private String apellido;
    
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    
    @Column(name = "semestre", nullable = false)
    private Integer semestre;
    
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;
    
    @Column(name = "genero", nullable = false, length = 10)
    private String genero;
    
    @Column(name = "telefono", nullable = false, length = 10)
    private String telefono;
    
    @Column(name = "programa", nullable = false, length = 100)
    private String programa;
    
    @Column(name = "universidad", nullable = false, length = 100)
    private String universidad;
    
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime updatedAt;
}

