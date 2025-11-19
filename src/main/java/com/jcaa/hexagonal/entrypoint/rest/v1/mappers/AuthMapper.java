package com.jcaa.hexagonal.entrypoint.rest.v1.mappers;

import com.jcaa.hexagonal.core.service.dto.ForgotPasswordRequest;
import com.jcaa.hexagonal.core.service.dto.LoginRequest;
import com.jcaa.hexagonal.core.service.dto.LoginResponse;
import com.jcaa.hexagonal.core.service.dto.RegisterRequest;
import com.jcaa.hexagonal.core.service.dto.ResetPasswordRequest;
import com.jcaa.hexagonal.entrypoint.rest.v1.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthMapper {
    
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);
    
    default LoginRequest toServiceLoginRequest(com.jcaa.hexagonal.entrypoint.rest.v1.dto.LoginRequest request) {
        LoginRequest serviceRequest = new LoginRequest();
        serviceRequest.setEmail(request.getEmail());
        serviceRequest.setPassword(request.getPassword());
        return serviceRequest;
    }
    
    default com.jcaa.hexagonal.entrypoint.rest.v1.dto.LoginResponse toLoginResponse(LoginResponse response) {
        return com.jcaa.hexagonal.entrypoint.rest.v1.dto.LoginResponse.builder()
                .accessToken(response.getAccessToken())
                .userId(response.getUserId())
                .userName(response.getUserName())
                .email(response.getEmail())
                .role(response.getRole())
                .build();
    }
    
    default RegisterRequest toServiceRegisterRequest(com.jcaa.hexagonal.entrypoint.rest.v1.dto.RegisterRequest request) {
        RegisterRequest serviceRequest = new RegisterRequest();
        serviceRequest.setUserName(request.getUserName());
        serviceRequest.setEmail(request.getEmail());
        serviceRequest.setPassword(request.getPassword());
        serviceRequest.setRole(request.getRole());
        return serviceRequest;
    }
    
    default ForgotPasswordRequest toServiceForgotPasswordRequest(com.jcaa.hexagonal.entrypoint.rest.v1.dto.ForgotPasswordRequest request) {
        ForgotPasswordRequest serviceRequest = new ForgotPasswordRequest();
        serviceRequest.setEmail(request.getEmail());
        return serviceRequest;
    }
    
    default ResetPasswordRequest toServiceResetPasswordRequest(com.jcaa.hexagonal.entrypoint.rest.v1.dto.ResetPasswordRequest request) {
        ResetPasswordRequest serviceRequest = new ResetPasswordRequest();
        serviceRequest.setToken(request.getToken());
        serviceRequest.setNewPassword(request.getNewPassword());
        return serviceRequest;
    }
}
