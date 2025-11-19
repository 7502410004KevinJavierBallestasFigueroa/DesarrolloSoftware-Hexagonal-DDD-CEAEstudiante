package com.jcaa.hexagonal.core.service;

import com.jcaa.hexagonal.core.domin.exception.NotFoundException;
import com.jcaa.hexagonal.core.domin.exception.ValidationException;
import com.jcaa.hexagonal.core.domin.users.Email;
import com.jcaa.hexagonal.core.domin.users.PasswordHash;
import com.jcaa.hexagonal.core.domin.users.Role;
import com.jcaa.hexagonal.core.domin.users.UserAccount;
import com.jcaa.hexagonal.core.domin.users.UserId;
import com.jcaa.hexagonal.core.domin.users.UserName;
import com.jcaa.hexagonal.core.port.EmailSenderPort;
import com.jcaa.hexagonal.core.port.PasswordHasherPort;
import com.jcaa.hexagonal.core.port.PasswordResetTokenStore;
import com.jcaa.hexagonal.core.port.TokenBlacklistStore;
import com.jcaa.hexagonal.core.port.TokenIssuerPort;
import com.jcaa.hexagonal.core.port.UsuarioRepository;
import com.jcaa.hexagonal.core.service.dto.ForgotPasswordRequest;
import com.jcaa.hexagonal.core.service.dto.LoginRequest;
import com.jcaa.hexagonal.core.service.dto.LoginResponse;
import com.jcaa.hexagonal.core.service.dto.RegisterRequest;
import com.jcaa.hexagonal.core.service.dto.ResetPasswordRequest;
import com.jcaa.hexagonal.core.service.dto.UsuarioDto;
import com.jcaa.hexagonal.core.service.mappers.UsuarioServiceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordHasherPort passwordHasher;
    private final TokenIssuerPort tokenIssuer;
    private final TokenBlacklistStore tokenBlacklistStore;
    private final PasswordResetTokenStore passwordResetTokenStore;
    private final EmailSenderPort emailSender;
    
    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordHasherPort passwordHasher,
            TokenIssuerPort tokenIssuer,
            TokenBlacklistStore tokenBlacklistStore,
            PasswordResetTokenStore passwordResetTokenStore,
            EmailSenderPort emailSender) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
        this.tokenIssuer = tokenIssuer;
        this.tokenBlacklistStore = tokenBlacklistStore;
        this.passwordResetTokenStore = passwordResetTokenStore;
        this.emailSender = emailSender;
    }
    
    public LoginResponse login(LoginRequest request) {
        Email email = new Email(request.getEmail());
        UserAccount account = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ValidationException("Credenciales inválidas"));
        
        ensureUsuarioActivo(account);
        
        if (!passwordHasher.verify(request.getPassword(), account.getPasswordHash().getValue())) {
            throw new ValidationException("Credenciales inválidas");
        }
        
        String token = tokenIssuer.generateToken(
                account.getId().getValue(),
                account.getUserName().getValue(),
                account.getRole().getValue()
        );
        
        return LoginResponse.builder()
                .accessToken(token)
                .userId(account.getId().getValue())
                .userName(account.getUserName().getValue())
                .email(account.getEmail().getValue())
                .role(account.getRole().getValue())
                .build();
    }
    
    public UsuarioDto register(RegisterRequest request) {
        Email email = new Email(request.getEmail());
        UserName userName = new UserName(request.getUserName());
        Role role = resolveRole(request.getRole());
        
        if (usuarioRepository.existsByEmail(email)) {
            throw new ValidationException("El email ya está registrado");
        }
        
        if (usuarioRepository.existsByUserName(userName)) {
            throw new ValidationException("El nombre de usuario ya está en uso");
        }
        
        String passwordHash = passwordHasher.hash(request.getPassword());
        UserAccount account = UserAccount.create(
                userName,
                email,
                new PasswordHash(passwordHash),
                role
        );
        
        account = usuarioRepository.save(account);
        
        return UsuarioServiceMapper.INSTANCE.toDto(account);
    }
    
    public void logout(String token) {
        if (token == null) {
            return;
        }
        if (!tokenIssuer.validateToken(token)) {
            return;
        }
        long expirationTime = tokenIssuer.getExpirationTimeFromToken(token);
        tokenBlacklistStore.addToken(token, expirationTime);
    }
    
    public void forgotPassword(ForgotPasswordRequest request) {
        Email email = new Email(request.getEmail());
        UserAccount account = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario", request.getEmail()));
        
        ensureUsuarioActivo(account);
        
        // Generar token de reseteo
        String resetToken = UUID.randomUUID().toString();
        long expirationTime = System.currentTimeMillis() + (60 * 60 * 1000); // 1 hora
        
        passwordResetTokenStore.saveResetToken(account.getId().getValue(), resetToken, expirationTime);
        
        // Enviar email
        String subject = "Recuperación de contraseña";
        String body = String.format(
                "Hola,\n\n" +
                "Has solicitado recuperar tu contraseña. Usa el siguiente token para resetearla:\n\n" +
                "Token: %s\n\n" +
                "Este token expirará en 1 hora.\n\n" +
                "Si no solicitaste este cambio, ignora este email.\n\n" +
                "Saludos,\n" +
                "Equipo de Soporte",
                resetToken
        );
        emailSender.sendEmail(account.getEmail().getValue(), subject, body);
    }
    
    public void resetPassword(ResetPasswordRequest request) {
        if (!passwordResetTokenStore.isValidToken(request.getToken())) {
            throw new ValidationException("Token inválido o expirado");
        }
        
        UUID userId = passwordResetTokenStore.getUserIdByToken(request.getToken());
        if (userId == null) {
            throw new ValidationException("Token inválido");
        }
        
        UserAccount account = usuarioRepository.findById(new UserId(userId))
                .orElseThrow(() -> new NotFoundException("Usuario", userId));
        
        String newPasswordHash = passwordHasher.hash(request.getNewPassword());
        account.changePassword(new PasswordHash(newPasswordHash));
        
        usuarioRepository.save(account);
        passwordResetTokenStore.deleteToken(request.getToken());
    }
    
    private void ensureUsuarioActivo(UserAccount account) {
        if (!account.isActive()) {
            throw new ValidationException("Usuario inactivo");
        }
    }
    
    private Role resolveRole(String role) {
        return new Role(role != null ? role : "User");
    }
}

