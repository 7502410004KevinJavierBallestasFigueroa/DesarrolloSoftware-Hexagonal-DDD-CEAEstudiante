package com.jcaa.hexagonal.entrypoint.rest.v1;

import com.jcaa.hexagonal.core.service.EstudianteService;
import com.jcaa.hexagonal.core.service.dto.EstudianteDto;
import com.jcaa.hexagonal.entrypoint.rest.v1.dto.EstudianteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/estudiantes")
@Tag(name = "Estudiantes", description = "Gestión de estudiantes (contexto CEA)")
@SecurityRequirement(name = "bearerAuth")
public class EstudianteController {
    
    private final EstudianteService estudianteService;
    
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registrar estudiante", description = "Crea un nuevo estudiante cumpliendo las reglas del dominio")
    public ResponseEntity<EstudianteResponse> registrar(@Valid @RequestBody RegistrarEstudianteRequest request) {
        EstudianteDto estudiante = estudianteService.registrarEstudiante(
                request.getNombre(),
                request.getApellido(),
                request.getFechaNacimiento(),
                request.getSemestre(),
                request.getEmail(),
                request.getGenero(),
                request.getTelefono(),
                request.getPrograma(),
                request.getUniversidad()
        );
        return new ResponseEntity<>(toResponse(estudiante), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','User')")
    @Operation(summary = "Obtener estudiante por ID", description = "Obtiene los datos de un estudiante por su identificador")
    public ResponseEntity<EstudianteResponse> obtenerPorId(@PathVariable UUID id) {
        EstudianteDto estudiante = estudianteService.obtenerPorId(id);
        return ResponseEntity.ok(toResponse(estudiante));
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','User')")
    @Operation(summary = "Listar estudiantes", description = "Obtiene una lista paginada de estudiantes")
    public ResponseEntity<Page<EstudianteResponse>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EstudianteDto> estudiantes = estudianteService.listar(pageable);
        Page<EstudianteResponse> response = estudiantes.map(this::toResponse);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar estudiante", description = "Actualiza los datos de un estudiante")
    public ResponseEntity<EstudianteResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ActualizarEstudianteRequest request) {
        EstudianteDto estudiante = estudianteService.actualizarEstudiante(
                id,
                request.getNombre(),
                request.getApellido(),
                request.getFechaNacimiento(),
                request.getSemestre(),
                request.getEmail(),
                request.getGenero(),
                request.getTelefono(),
                request.getPrograma(),
                request.getUniversidad()
        );
        return ResponseEntity.ok(toResponse(estudiante));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar estudiante", description = "Elimina un estudiante por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        estudianteService.eliminarEstudiante(id);
        return ResponseEntity.noContent().build();
    }
    
    private EstudianteResponse toResponse(EstudianteDto dto) {
        return EstudianteResponse.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .fechaNacimiento(dto.getFechaNacimiento())
                .semestre(dto.getSemestre())
                .email(dto.getEmail())
                .genero(dto.getGenero())
                .telefono(dto.getTelefono())
                .programa(dto.getPrograma())
                .universidad(dto.getUniversidad())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
    
    public static class RegistrarEstudianteRequest {
        
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 3, max = 20, message = "El nombre debe tener entre 3 y 20 caracteres")
        private String nombre;
        
        @NotBlank(message = "El apellido es requerido")
        @Size(min = 3, max = 20, message = "El apellido debe tener entre 3 y 20 caracteres")
        private String apellido;
        
        @NotNull(message = "La fecha de nacimiento es requerida")
        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        private LocalDate fechaNacimiento;
        
        @NotNull(message = "El semestre es requerido")
        @Min(value = 1, message = "El semestre mínimo es 1")
        @Max(value = 10, message = "El semestre máximo es 10")
        private Integer semestre;
        
        @NotBlank(message = "El email es requerido")
        @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.(com|co)$",
                 flags = jakarta.validation.constraints.Pattern.Flag.CASE_INSENSITIVE,
                 message = "El email debe contener '@' y terminar en .com o .co")
        private String email;
        
        @NotBlank(message = "El género es requerido")
        private String genero;
        
        @NotBlank(message = "El teléfono es requerido")
        @Pattern(regexp = "\\d{10}", message = "El teléfono debe contener exactamente 10 dígitos")
        private String telefono;
        
        @NotBlank(message = "El programa es requerido")
        private String programa;
        
        @NotBlank(message = "La universidad es requerida")
        private String universidad;
        
        public String getNombre() {
            return nombre;
        }
        
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        public String getApellido() {
            return apellido;
        }
        
        public void setApellido(String apellido) {
            this.apellido = apellido;
        }
        
        public LocalDate getFechaNacimiento() {
            return fechaNacimiento;
        }
        
        public void setFechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }
        
        public Integer getSemestre() {
            return semestre;
        }
        
        public void setSemestre(Integer semestre) {
            this.semestre = semestre;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getGenero() {
            return genero;
        }
        
        public void setGenero(String genero) {
            this.genero = genero;
        }
        
        public String getTelefono() {
            return telefono;
        }
        
        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
        
        public String getPrograma() {
            return programa;
        }
        
        public void setPrograma(String programa) {
            this.programa = programa;
        }
        
        public String getUniversidad() {
            return universidad;
        }
        
        public void setUniversidad(String universidad) {
            this.universidad = universidad;
        }
    }
    
    public static class ActualizarEstudianteRequest {
        
        @Size(min = 3, max = 20, message = "El nombre debe tener entre 3 y 20 caracteres")
        private String nombre;
        
        @Size(min = 3, max = 20, message = "El apellido debe tener entre 3 y 20 caracteres")
        private String apellido;
        
        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        private LocalDate fechaNacimiento;
        
        @Min(value = 1, message = "El semestre mínimo es 1")
        @Max(value = 10, message = "El semestre máximo es 10")
        private Integer semestre;
        
        @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.(com|co)$",
                 flags = jakarta.validation.constraints.Pattern.Flag.CASE_INSENSITIVE,
                 message = "El email debe contener '@' y terminar en .com o .co")
        private String email;
        
        private String genero;
        
        @Pattern(regexp = "\\d{10}", message = "El teléfono debe contener exactamente 10 dígitos")
        private String telefono;
        
        private String programa;
        
        private String universidad;
        
        public String getNombre() {
            return nombre;
        }
        
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        public String getApellido() {
            return apellido;
        }
        
        public void setApellido(String apellido) {
            this.apellido = apellido;
        }
        
        public LocalDate getFechaNacimiento() {
            return fechaNacimiento;
        }
        
        public void setFechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }
        
        public Integer getSemestre() {
            return semestre;
        }
        
        public void setSemestre(Integer semestre) {
            this.semestre = semestre;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getGenero() {
            return genero;
        }
        
        public void setGenero(String genero) {
            this.genero = genero;
        }
        
        public String getTelefono() {
            return telefono;
        }
        
        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
        
        public String getPrograma() {
            return programa;
        }
        
        public void setPrograma(String programa) {
            this.programa = programa;
        }
        
        public String getUniversidad() {
            return universidad;
        }
        
        public void setUniversidad(String universidad) {
            this.universidad = universidad;
        }
    }
}
