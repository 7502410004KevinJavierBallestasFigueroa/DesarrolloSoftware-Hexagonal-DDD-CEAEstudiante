package com.jcaa.hexagonal.adapter.databases.sql.mappers;

import com.jcaa.hexagonal.adapter.databases.sql.EstudianteEntity;
import com.jcaa.hexagonal.core.domin.estudiantes.Apellido;
import com.jcaa.hexagonal.core.domin.estudiantes.Estudiante;
import com.jcaa.hexagonal.core.domin.estudiantes.EstudianteEmail;
import com.jcaa.hexagonal.core.domin.estudiantes.EstudianteId;
import com.jcaa.hexagonal.core.domin.estudiantes.FechaNacimiento;
import com.jcaa.hexagonal.core.domin.estudiantes.Genero;
import com.jcaa.hexagonal.core.domin.estudiantes.Nombre;
import com.jcaa.hexagonal.core.domin.estudiantes.Programa;
import com.jcaa.hexagonal.core.domin.estudiantes.Semestre;
import com.jcaa.hexagonal.core.domin.estudiantes.Telefono;
import com.jcaa.hexagonal.core.domin.estudiantes.Universidad;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EstudianteEntityMapper {
    
    EstudianteEntityMapper INSTANCE = Mappers.getMapper(EstudianteEntityMapper.class);
    
    default EstudianteEntity toEntity(Estudiante domain) {
        if (domain == null) {
            return null;
        }
        
        return EstudianteEntity.builder()
                .id(domain.getId().getValue())
                .nombre(domain.getNombre().getValue())
                .apellido(domain.getApellido().getValue())
                .fechaNacimiento(domain.getFechaNacimiento().getValue())
                .semestre(domain.getSemestre().getValue())
                .email(domain.getEmail().getValue())
                .genero(domain.getGenero().getValue())
                .telefono(domain.getTelefono().getValue())
                .programa(domain.getPrograma().getValue())
                .universidad(domain.getUniversidad().getValue())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
    
    default Estudiante toDomain(EstudianteEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Estudiante.reconstitute(
                new EstudianteId(entity.getId()),
                new Nombre(entity.getNombre()),
                new Apellido(entity.getApellido()),
                new FechaNacimiento(entity.getFechaNacimiento()),
                new Semestre(entity.getSemestre()),
                new EstudianteEmail(entity.getEmail()),
                new Genero(entity.getGenero()),
                new Telefono(entity.getTelefono()),
                new Programa(entity.getPrograma()),
                new Universidad(entity.getUniversidad()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
    
    default void updateEntity(Estudiante domain, EstudianteEntity entity) {
        if (domain == null || entity == null) {
            return;
        }
        
        entity.setNombre(domain.getNombre().getValue());
        entity.setApellido(domain.getApellido().getValue());
        entity.setFechaNacimiento(domain.getFechaNacimiento().getValue());
        entity.setSemestre(domain.getSemestre().getValue());
        entity.setEmail(domain.getEmail().getValue());
        entity.setGenero(domain.getGenero().getValue());
        entity.setTelefono(domain.getTelefono().getValue());
        entity.setPrograma(domain.getPrograma().getValue());
        entity.setUniversidad(domain.getUniversidad().getValue());
        entity.setUpdatedAt(domain.getUpdatedAt());
    }
}

