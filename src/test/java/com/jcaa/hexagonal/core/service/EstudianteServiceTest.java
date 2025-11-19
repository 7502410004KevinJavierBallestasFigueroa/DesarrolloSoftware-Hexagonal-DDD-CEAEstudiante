package com.jcaa.hexagonal.core.service;

import com.jcaa.hexagonal.core.domin.estudiantes.Apellido;
import com.jcaa.hexagonal.core.domin.estudiantes.Estudiante;
import com.jcaa.hexagonal.core.domin.estudiantes.EstudianteEmail;
import com.jcaa.hexagonal.core.domin.estudiantes.EstudianteId;
import com.jcaa.hexagonal.core.domin.estudiantes.FechaNacimiento;
import com.jcaa.hexagonal.core.domin.estudiantes.Genero;
import com.jcaa.hexagonal.core.domin.estudiantes.Nombre;
import com.jcaa.hexagonal.core.domin.estudiantes.Programa;
import com.jcaa.hexagonal.core.domin.estudiantes.Semestre;
import com.jcaa.hexagonal.core.domin.estudiantes.Telefono;
import com.jcaa.hexagonal.core.domin.estudiantes.Universidad;
import com.jcaa.hexagonal.core.domin.exception.ValidationException;
import com.jcaa.hexagonal.core.port.EstudianteRepository;
import com.jcaa.hexagonal.core.service.dto.EstudianteDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {
    
    @Mock
    private EstudianteRepository estudianteRepository;
    
    @InjectMocks
    private EstudianteService estudianteService;
    
    @Test
    void registrarEstudianteShouldThrowWhenEmailAlreadyExists() {
        doReturn(true).when(estudianteRepository).existsByEmail(any(EstudianteEmail.class));
        
        assertThrows(
                ValidationException.class,
                () -> estudianteService.registrarEstudiante(
                        "Juan",
                        "Perez",
                        LocalDate.now().minusYears(18),
                        3,
                        "juan.perez@example.com",
                        "M",
                        "3001234567",
                        "Ingenieria de Sistemas",
                        "Universidad de Ejemplo"
                )
        );
    }
    
    @Test
    void registrarEstudianteShouldPersistWhenDataIsValid() {
        doReturn(false).when(estudianteRepository).existsByEmail(any(EstudianteEmail.class));
        
        ArgumentCaptor<Estudiante> estudianteCaptor = ArgumentCaptor.forClass(Estudiante.class);
        doReturn(createSampleEstudiante()).when(estudianteRepository).save(any(Estudiante.class));
        
        EstudianteDto dto = estudianteService.registrarEstudiante(
                "Ana",
                "Lopez",
                LocalDate.now().minusYears(20),
                2,
                "ana.lopez@example.com",
                "F",
                "3000000000",
                "Ingenieria Industrial",
                "Universidad de Ejemplo"
        );
        
        verify(estudianteRepository).save(estudianteCaptor.capture());
        Estudiante saved = estudianteCaptor.getValue();
        assertEquals("Ana", saved.getNombre().getValue());
        assertEquals("Lopez", saved.getApellido().getValue());
        assertNotNull(dto.getId());
        assertEquals("ana.lopez@example.com", dto.getEmail());
    }
    
    @Test
    void actualizarEstudianteShouldCheckEmailUniquenessWhenEmailChanges() {
        UUID id = UUID.randomUUID();
        Estudiante existente = createSampleEstudiante();
        doReturn(Optional.of(existente)).when(estudianteRepository).findById(new EstudianteId(id));
        
        doReturn(true).when(estudianteRepository).existsByEmail(any(EstudianteEmail.class));
        
        assertThrows(
                ValidationException.class,
                () -> estudianteService.actualizarEstudiante(
                        id,
                        null,
                        null,
                        null,
                        null,
                        "nuevo.email@example.com",
                        null,
                        null,
                        null,
                        null
                )
        );
    }
    
    private Estudiante createSampleEstudiante() {
        LocalDate fechaNacimiento = LocalDate.now().minusYears(22);
        return Estudiante.reconstitute(
                EstudianteId.newId(),
                new Nombre("Ana"),
                new Apellido("Lopez"),
                new FechaNacimiento(fechaNacimiento),
                new Semestre(2),
                new EstudianteEmail("ana.lopez@example.com"),
                new Genero("F"),
                new Telefono("3000000000"),
                new Programa("Ingenieria Industrial"),
                new Universidad("Universidad de Ejemplo"),
                java.time.LocalDateTime.now(),
                null
        );
    }
}
