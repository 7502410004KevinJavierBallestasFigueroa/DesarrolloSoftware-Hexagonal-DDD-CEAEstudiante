package com.jcaa.hexagonal.core.service.mappers;

import com.jcaa.hexagonal.core.domin.estudiantes.Estudiante;
import com.jcaa.hexagonal.core.service.dto.EstudianteDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-18T00:25:02-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
public class EstudianteServiceMapperImpl implements EstudianteServiceMapper {

    @Override
    public EstudianteDto toDto(Estudiante domain) {
        if ( domain == null ) {
            return null;
        }

        EstudianteDto.EstudianteDtoBuilder estudianteDto = EstudianteDto.builder();

        estudianteDto.createdAt( domain.getCreatedAt() );
        estudianteDto.updatedAt( domain.getUpdatedAt() );

        estudianteDto.id( domain.getId().getValue() );
        estudianteDto.nombre( domain.getNombre().getValue() );
        estudianteDto.apellido( domain.getApellido().getValue() );
        estudianteDto.fechaNacimiento( domain.getFechaNacimiento().getValue() );
        estudianteDto.semestre( domain.getSemestre().getValue() );
        estudianteDto.email( domain.getEmail().getValue() );
        estudianteDto.genero( domain.getGenero().getValue() );
        estudianteDto.telefono( domain.getTelefono().getValue() );
        estudianteDto.programa( domain.getPrograma().getValue() );
        estudianteDto.universidad( domain.getUniversidad().getValue() );

        return estudianteDto.build();
    }
}
