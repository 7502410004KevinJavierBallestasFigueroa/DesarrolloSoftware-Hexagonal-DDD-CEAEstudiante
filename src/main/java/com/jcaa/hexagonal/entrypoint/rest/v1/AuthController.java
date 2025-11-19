package com.jcaa.hexagonal.entrypoint.rest.v1;

import com.jcaa.hexagonal.core.service.AuthService;
import com.jcaa.hexagonal.core.service.dto.UsuarioDto;
import com.jcaa.hexagonal.entrypoint.rest.v1.dto.*;
import com.jcaa.hexagonal.entrypoint.rest.v1.mappers.AuthMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints de autenticación y gestión de usuarios")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario", description = "Registro público de nuevo usuario")
    public ResponseEntity<UsuarioResponse> register(@Valid @RequestBody RegisterRequest request) {
        com.jcaa.hexagonal.core.service.dto.RegisterRequest serviceRequest =
                AuthMapper.INSTANCE.toServiceRegisterRequest(request);
        UsuarioDto usuarioDto = authService.register(serviceRequest);
        
        UsuarioResponse response = toUsuarioResponse(usuarioDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autenticación de usuario y obtención de JWT token")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        com.jcaa.hexagonal.core.service.dto.LoginRequest serviceRequest =
                AuthMapper.INSTANCE.toServiceLoginRequest(request);
        com.jcaa.hexagonal.core.service.dto.LoginResponse serviceResponse = authService.login(serviceRequest);
        
        LoginResponse response = AuthMapper.INSTANCE.toLoginResponse(serviceResponse);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Invalidar token JWT (agregar a blacklist)")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            authService.logout(token);
        }
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/password/forgot")
    @Operation(summary = "Solicitar recuperación de contraseña", description = "Genera token de reseteo y envía email")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        com.jcaa.hexagonal.core.service.dto.ForgotPasswordRequest serviceRequest =
                AuthMapper.INSTANCE.toServiceForgotPasswordRequest(request);
        authService.forgotPassword(serviceRequest);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/password/reset")
    @Operation(summary = "Resetear contraseña", description = "Restablecer contraseña usando token de reseteo")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        com.jcaa.hexagonal.core.service.dto.ResetPasswordRequest serviceRequest =
                AuthMapper.INSTANCE.toServiceResetPasswordRequest(request);
        authService.resetPassword(serviceRequest);
        return ResponseEntity.ok().build();
    }
    
    private UsuarioResponse toUsuarioResponse(UsuarioDto usuarioDto) {
        return UsuarioResponse.builder()
                .id(usuarioDto.getId())
                .userName(usuarioDto.getUserName())
                .email(usuarioDto.getEmail())
                .role(usuarioDto.getRole())
                .isActive(usuarioDto.isActive())
                .createdAt(usuarioDto.getCreatedAt())
                .updatedAt(usuarioDto.getUpdatedAt())
                .build();
    }
}
