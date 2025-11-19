package com.jcaa.hexagonal.core.service;

import com.jcaa.hexagonal.core.domin.exception.NotFoundException;
import com.jcaa.hexagonal.core.domin.exception.ValidationException;
import com.jcaa.hexagonal.core.domin.users.Email;
import com.jcaa.hexagonal.core.domin.users.PasswordHash;
import com.jcaa.hexagonal.core.domin.users.Role;
import com.jcaa.hexagonal.core.domin.users.UserAccount;
import com.jcaa.hexagonal.core.domin.users.UserId;
import com.jcaa.hexagonal.core.domin.users.UserName;
import com.jcaa.hexagonal.core.port.PasswordHasherPort;
import com.jcaa.hexagonal.core.port.UsuarioRepository;
import com.jcaa.hexagonal.core.service.dto.UsuarioDto;
import com.jcaa.hexagonal.core.service.mappers.UsuarioServiceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordHasherPort passwordHasher;
    
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordHasherPort passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
    }
    
    public UsuarioDto createUsuario(String userName, String email, String password, String role) {
        Email emailVo = new Email(email);
        UserName userNameVo = new UserName(userName);
        Role roleVo = resolveRole(role);
        
        if (usuarioRepository.existsByEmail(emailVo)) {
            throw new ValidationException("El email ya está registrado");
        }
        
        if (usuarioRepository.existsByUserName(userNameVo)) {
            throw new ValidationException("El nombre de usuario ya está en uso");
        }
        
        String passwordHash = passwordHasher.hash(password);
        UserAccount account = UserAccount.create(userNameVo, emailVo, new PasswordHash(passwordHash), roleVo);
        account = usuarioRepository.save(account);
        
        return UsuarioServiceMapper.INSTANCE.toDto(account);
    }
    
    @Transactional(readOnly = true)
    public UsuarioDto getUsuarioById(UUID id) {
        UserAccount account = usuarioRepository.findById(new UserId(id))
                .orElseThrow(() -> new NotFoundException("Usuario", id));
        return UsuarioServiceMapper.INSTANCE.toDto(account);
    }
    
    @Transactional(readOnly = true)
    public Page<UsuarioDto> getAllUsuarios(Pageable pageable) {
        Page<UserAccount> accounts = usuarioRepository.findAll(pageable);
        return accounts.map(UsuarioServiceMapper.INSTANCE::toDto);
    }
    
    public UsuarioDto updateUsuario(UUID id, String userName, String email, String role, Boolean isActive) {
        UserAccount account = usuarioRepository.findById(new UserId(id))
                .orElseThrow(() -> new NotFoundException("Usuario", id));
        
        if (userName != null) {
            UserName newUserName = new UserName(userName);
            if (usuarioRepository.existsByUserName(newUserName) &&
                !account.getUserName().equals(newUserName)) {
                throw new ValidationException("El nombre de usuario ya está en uso");
            }
            account.rename(newUserName);
        }
        
        if (email != null) {
            Email newEmail = new Email(email);
            if (usuarioRepository.existsByEmail(newEmail) &&
                !account.getEmail().equals(newEmail)) {
                throw new ValidationException("El email ya está registrado");
            }
            account.updateEmail(newEmail);
        }
        
        if (role != null) {
            account.assignRole(new Role(role));
        }
        
        if (isActive != null) {
            if (isActive && !account.isActive()) {
                account.reactivate();
            } else if (!isActive && account.isActive()) {
                account.deactivate();
            }
        }
        
        account = usuarioRepository.save(account);
        return UsuarioServiceMapper.INSTANCE.toDto(account);
    }
    
    public void deleteUsuario(UUID id) {
        UserAccount account = usuarioRepository.findById(new UserId(id))
                .orElseThrow(() -> new NotFoundException("Usuario", id));
        usuarioRepository.delete(account.getId());
    }
    
    @Transactional(readOnly = true)
    public UsuarioDto getCurrentUsuario(UUID userId) {
        return getUsuarioById(userId);
    }
    
    private Role resolveRole(String role) {
        return new Role(role != null ? role : "User");
    }
}

