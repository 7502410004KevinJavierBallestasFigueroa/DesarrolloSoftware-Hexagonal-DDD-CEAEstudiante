package com.jcaa.hexagonal.core.domin.estudiantes;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EstudianteDomainTest {
    
    @Test
    void shouldCreateEstudianteWhenDataIsValid() {
        LocalDate fechaNacimiento = LocalDate.now().minusYears(18);
        
        Estudiante estudiante = Estudiante.registrar(
                new Nombre("Juan"),
                new Apellido("Perez"),
                new FechaNacimiento(fechaNacimiento),
                new Semestre(3),
                new EstudianteEmail("juan.perez@example.com"),
                new Genero("M"),
                new Telefono("3001234567"),
                new Programa("Ingenieria de Sistemas"),
                new Universidad("Universidad de Ejemplo")
        );
        
        assertNotNull(estudiante.getId());
        assertEquals("Juan", estudiante.getNombre().getValue());
        assertEquals("Perez", estudiante.getApellido().getValue());
        assertEquals(3, estudiante.getSemestre().getValue());
        assertEquals("juan.perez@example.com", estudiante.getEmail().getValue());
        assertNotNull(estudiante.getCreatedAt());
    }
    
    @Test
    void shouldFailWhenAgeIsLessThan15() {
        LocalDate fechaNacimientoInvalida = LocalDate.now().minusYears(10);
        assertThrows(IllegalArgumentException.class, () -> new FechaNacimiento(fechaNacimientoInvalida));
    }
    
    @Test
    void shouldUpdateSemestreAndSetUpdatedAt() {
        LocalDate fechaNacimiento = LocalDate.now().minusYears(20);
        Estudiante estudiante = Estudiante.registrar(
                new Nombre("Ana"),
                new Apellido("Lopez"),
                new FechaNacimiento(fechaNacimiento),
                new Semestre(1),
                new EstudianteEmail("ana.lopez@example.com"),
                new Genero("F"),
                new Telefono("3000000000"),
                new Programa("Ingenieria Industrial"),
                new Universidad("Universidad de Ejemplo")
        );
        
        assertEquals(1, estudiante.getSemestre().getValue());
        assertNotNull(estudiante.getCreatedAt());
        
        // Ejecutar
        estudiante.actualizarSemestre(new Semestre(2));
        
        // Verificar
        assertEquals(2, estudiante.getSemestre().getValue());
        assertNotNull(estudiante.getUpdatedAt());
    }
    
    @Test
    void shouldValidateTelefonoWithExactlyTenDigits() {
        assertDoesNotThrow(() -> new Telefono("1234567890"));
        assertThrows(IllegalArgumentException.class, () -> new Telefono("12345"));
        assertThrows(IllegalArgumentException.class, () -> new Telefono("12345678901"));
        assertThrows(IllegalArgumentException.class, () -> new Telefono("12345abcde"));
    }
}

