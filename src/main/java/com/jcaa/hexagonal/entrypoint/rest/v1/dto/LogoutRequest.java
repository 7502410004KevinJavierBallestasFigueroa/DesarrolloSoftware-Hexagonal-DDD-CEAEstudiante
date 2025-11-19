package com.jcaa.hexagonal.entrypoint.rest.v1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogoutRequest {
    @NotBlank(message = "El token es requerido")
    private String token;
}

