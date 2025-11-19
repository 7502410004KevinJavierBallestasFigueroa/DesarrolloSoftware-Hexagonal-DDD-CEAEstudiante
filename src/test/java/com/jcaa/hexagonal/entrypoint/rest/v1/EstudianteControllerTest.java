package com.jcaa.hexagonal.entrypoint.rest.v1;

import com.jcaa.hexagonal.core.service.EstudianteService;
import com.jcaa.hexagonal.core.service.dto.EstudianteDto;
import com.jcaa.hexagonal.entrypoint.rest.v1.dto.EstudianteResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class EstudianteControllerTest {
    
    @Test
    void registrarShouldReturnCreatedResponse() {
        EstudianteService estudianteService = mock(EstudianteService.class);
        EstudianteController controller = new EstudianteController(estudianteService);
        
        EstudianteDto dto = EstudianteDto.builder()
                .id(UUID.randomUUID())
                .nombre("Juan")
                .apellido("Perez")
                .fechaNacimiento(LocalDate.now().minusYears(18))
                .semestre(3)
                .email("juan.perez@example.com")
                .genero("M")
                .telefono("3001234567")
                .programa("Ingenieria de Sistemas")
                .universidad("Universidad de Ejemplo")
                .createdAt(LocalDateTime.now())
                .build();
        
        doReturn(dto).when(estudianteService).registrarEstudiante(
                any(), any(), any(), any(), any(), any(), any(), any(), any()
        );
        
        EstudianteController.RegistrarEstudianteRequest request =
                new EstudianteController.RegistrarEstudianteRequest();
        request.setNombre("Juan");
        request.setApellido("Perez");
        request.setFechaNacimiento(LocalDate.now().minusYears(18));
        request.setSemestre(3);
        request.setEmail("juan.perez@example.com");
        request.setGenero("M");
        request.setTelefono("3001234567");
        request.setPrograma("Ingenieria de Sistemas");
        request.setUniversidad("Universidad de Ejemplo");
        
        var responseEntity = controller.registrar(request);
        
        assertEquals(201, responseEntity.getStatusCode().value());
        EstudianteResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Juan", body.getNombre());
        assertEquals("Perez", body.getApellido());
        assertEquals("juan.perez@example.com", body.getEmail());
    }
}

