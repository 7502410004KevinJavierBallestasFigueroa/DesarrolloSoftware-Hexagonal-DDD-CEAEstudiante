package com.jcaa.hexagonal.entrypoint.rest.v1;

import com.jcaa.hexagonal.core.service.UsuarioService;
import com.jcaa.hexagonal.core.service.dto.UsuarioDto;
import com.jcaa.hexagonal.entrypoint.rest.v1.dto.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Gestión de usuarios (requiere rol ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar usuarios", description = "Obtener lista paginada de usuarios (solo ADMIN)")
    public ResponseEntity<Page<UsuarioResponse>> getAllUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UsuarioDto> usuarios = usuarioService.getAllUsuarios(pageable);
        Page<UsuarioResponse> response = usuarios.map(this::toResponse);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener usuario por ID", description = "Obtener detalles de un usuario (solo ADMIN)")
    public ResponseEntity<UsuarioResponse> getUsuarioById(@PathVariable UUID id) {
        UsuarioDto usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(toResponse(usuario));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear usuario", description = "Crear nuevo usuario (solo ADMIN)")
    public ResponseEntity<UsuarioResponse> createUsuario(@Valid @RequestBody CreateUsuarioRequest request) {
        UsuarioDto usuario = usuarioService.createUsuario(
                request.getUserName(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );
        return new ResponseEntity<>(toResponse(usuario), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar usuario", description = "Actualizar datos de un usuario (solo ADMIN)")
    public ResponseEntity<UsuarioResponse> updateUsuario(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUsuarioRequest request) {
        UsuarioDto usuario = usuarioService.updateUsuario(
                id,
                request.getUserName(),
                request.getEmail(),
                request.getRole(),
                request.getIsActive()
        );
        return ResponseEntity.ok(toResponse(usuario));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar usuario", description = "Eliminar un usuario (solo ADMIN)")
    public ResponseEntity<Void> deleteUsuario(@PathVariable UUID id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/me")
    @Operation(summary = "Obtener usuario actual", description = "Obtener información del usuario autenticado")
    public ResponseEntity<UsuarioResponse> getCurrentUsuario(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        UsuarioDto usuario = usuarioService.getCurrentUsuario(userId);
        return ResponseEntity.ok(toResponse(usuario));
    }
    
    private UsuarioResponse toResponse(UsuarioDto dto) {
        return UsuarioResponse.builder()
                .id(dto.getId())
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .role(dto.getRole())
                .isActive(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
    
    // DTOs internos
    public static class CreateUsuarioRequest {
        @jakarta.validation.constraints.NotBlank(message = "El nombre de usuario es requerido")
        @jakarta.validation.constraints.Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
        private String userName;
        
        @jakarta.validation.constraints.NotBlank(message = "El email es requerido")
        @jakarta.validation.constraints.Email(message = "El email debe tener un formato válido")
        private String email;
        
        @jakarta.validation.constraints.NotBlank(message = "La contraseña es requerida")
        @jakarta.validation.constraints.Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        private String password;
        
        private String role;
        
        // Getters y setters
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
    
    public static class UpdateUsuarioRequest {
        private String userName;
        private String email;
        private String role;
        private Boolean isActive;
        
        // Getters y setters
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public Boolean getIsActive() { return isActive; }
        public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    }
}

