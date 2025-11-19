package com.jcaa.hexagonal.core.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDto {
    private UUID id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private Integer semestre;
    private String email;
    private String genero;
    private String telefono;
    private String programa;
    private String universidad;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

