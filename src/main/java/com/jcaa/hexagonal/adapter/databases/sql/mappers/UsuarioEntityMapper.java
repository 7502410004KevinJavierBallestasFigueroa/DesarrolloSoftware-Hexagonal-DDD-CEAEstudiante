package com.jcaa.hexagonal.adapter.databases.sql.mappers;

import com.jcaa.hexagonal.adapter.databases.sql.UserAccountEntity;
import com.jcaa.hexagonal.core.domin.users.Email;
import com.jcaa.hexagonal.core.domin.users.PasswordHash;
import com.jcaa.hexagonal.core.domin.users.Role;
import com.jcaa.hexagonal.core.domin.users.UserAccount;
import com.jcaa.hexagonal.core.domin.users.UserId;
import com.jcaa.hexagonal.core.domin.users.UserName;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioEntityMapper {
    
    UsuarioEntityMapper INSTANCE = Mappers.getMapper(UsuarioEntityMapper.class);
    
    default UserAccountEntity toEntity(UserAccount domain) {
        if (domain == null) {
            return null;
        }
        
        return UserAccountEntity.builder()
                .id(domain.getId().getValue())
                .username(domain.getUserName().getValue())
                .email(domain.getEmail().getValue())
                .passwordHash(domain.getPasswordHash().getValue())
                .role(domain.getRole().getValue())
                .isActive(domain.isActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
    
    default UserAccount toDomain(UserAccountEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return UserAccount.reconstitute(
                new UserId(entity.getId()),
                new UserName(entity.getUsername()),
                new Email(entity.getEmail()),
                new PasswordHash(entity.getPasswordHash()),
                new Role(entity.getRole()),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
    
    default void updateEntity(UserAccount domain, UserAccountEntity entity) {
        if (domain == null || entity == null) {
            return;
        }
        
        entity.setUsername(domain.getUserName().getValue());
        entity.setEmail(domain.getEmail().getValue());
        entity.setPasswordHash(domain.getPasswordHash().getValue());
        entity.setRole(domain.getRole().getValue());
        entity.setIsActive(domain.isActive());
        entity.setUpdatedAt(domain.getUpdatedAt());
    }
}

