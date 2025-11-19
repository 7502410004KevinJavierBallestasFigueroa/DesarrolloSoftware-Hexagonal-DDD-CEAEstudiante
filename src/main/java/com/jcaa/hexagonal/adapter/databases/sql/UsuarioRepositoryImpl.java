package com.jcaa.hexagonal.adapter.databases.sql;

import com.jcaa.hexagonal.adapter.databases.sql.mappers.UsuarioEntityMapper;
import com.jcaa.hexagonal.core.domin.users.Email;
import com.jcaa.hexagonal.core.domin.users.UserAccount;
import com.jcaa.hexagonal.core.domin.users.UserId;
import com.jcaa.hexagonal.core.domin.users.UserName;
import com.jcaa.hexagonal.core.port.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {
    
    private final UserAccountJpaRepository jpaRepository;
    private final UsuarioEntityMapper mapper;
    
    public UsuarioRepositoryImpl(UserAccountJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
        this.mapper = UsuarioEntityMapper.INSTANCE;
    }
    
    @Override
    public UserAccount save(UserAccount usuario) {
        UserAccountEntity entity;
        
        // Si existe, cargar y actualizar; si no, crear nuevo
        if (usuario.getId() != null && jpaRepository.existsById(usuario.getId().getValue())) {
            entity = jpaRepository.findById(usuario.getId().getValue())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            // Preservar createdAt al actualizar
            java.time.LocalDateTime originalCreatedAt = entity.getCreatedAt();
            mapper.updateEntity(usuario, entity);
            entity.setCreatedAt(originalCreatedAt);
        } else {
            entity = mapper.toEntity(usuario);
            // Si es nuevo, establecer createdAt si no está establecido
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(java.time.LocalDateTime.now());
            }
        }
        
        UserAccountEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<UserAccount> findById(UserId id) {
        return jpaRepository.findById(id.getValue())
                .map(mapper::toDomain);
    }
    
    @Override
    public Optional<UserAccount> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.getValue())
                .map(mapper::toDomain);
    }
    
    @Override
    public Optional<UserAccount> findByUserName(UserName userName) {
        return jpaRepository.findByUsername(userName.getValue())
                .map(mapper::toDomain);
    }
    
    @Override
    public List<UserAccount> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<UserAccount> findAll(Pageable pageable) {
        Page<UserAccountEntity> entities = jpaRepository.findAll(pageable);
        return entities.map(mapper::toDomain);
    }
    
    @Override
    public void delete(UserId id) {
        jpaRepository.deleteById(id.getValue());
    }
    
    @Override
    public boolean existsByEmail(Email email) {
        return jpaRepository.existsByEmail(email.getValue());
    }
    
    @Override
    public boolean existsByUserName(UserName userName) {
        return jpaRepository.existsByUsername(userName.getValue());
    }
}

