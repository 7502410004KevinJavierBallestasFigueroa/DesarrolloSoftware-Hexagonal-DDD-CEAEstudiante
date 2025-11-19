package com.jcaa.hexagonal.core.service.mappers;

import com.jcaa.hexagonal.core.domin.users.UserAccount;
import com.jcaa.hexagonal.core.service.dto.UsuarioDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-18T00:25:02-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
public class UsuarioServiceMapperImpl implements UsuarioServiceMapper {

    @Override
    public UsuarioDto toDto(UserAccount domain) {
        if ( domain == null ) {
            return null;
        }

        UsuarioDto.UsuarioDtoBuilder usuarioDto = UsuarioDto.builder();

        usuarioDto.createdAt( domain.getCreatedAt() );
        usuarioDto.updatedAt( domain.getUpdatedAt() );

        usuarioDto.id( domain.getId().getValue() );
        usuarioDto.userName( domain.getUserName().getValue() );
        usuarioDto.email( domain.getEmail().getValue() );
        usuarioDto.role( domain.getRole().getValue() );

        return usuarioDto.build();
    }
}
