package com.jcaa.hexagonal.core.service.mappers;

import com.jcaa.hexagonal.core.domin.users.UserAccount;
import com.jcaa.hexagonal.core.service.dto.UsuarioDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioServiceMapper {
    
    UsuarioServiceMapper INSTANCE = Mappers.getMapper(UsuarioServiceMapper.class);
    
    @Mapping(target = "id", expression = "java(domain.getId().getValue())")
    @Mapping(target = "userName", expression = "java(domain.getUserName().getValue())")
    @Mapping(target = "email", expression = "java(domain.getEmail().getValue())")
    @Mapping(target = "role", expression = "java(domain.getRole().getValue())")
    UsuarioDto toDto(UserAccount domain);
}

