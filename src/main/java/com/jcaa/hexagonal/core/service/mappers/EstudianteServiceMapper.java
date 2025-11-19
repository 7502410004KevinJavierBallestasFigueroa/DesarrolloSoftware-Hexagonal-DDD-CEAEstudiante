package com.jcaa.hexagonal.core.service.mappers;

import com.jcaa.hexagonal.core.domin.estudiantes.Estudiante;
import com.jcaa.hexagonal.core.service.dto.EstudianteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EstudianteServiceMapper {
    
    EstudianteServiceMapper INSTANCE = Mappers.getMapper(EstudianteServiceMapper.class);
    
    @Mapping(target = "id", expression = "java(domain.getId().getValue())")
    @Mapping(target = "nombre", expression = "java(domain.getNombre().getValue())")
    @Mapping(target = "apellido", expression = "java(domain.getApellido().getValue())")
    @Mapping(target = "fechaNacimiento", expression = "java(domain.getFechaNacimiento().getValue())")
    @Mapping(target = "semestre", expression = "java(domain.getSemestre().getValue())")
    @Mapping(target = "email", expression = "java(domain.getEmail().getValue())")
    @Mapping(target = "genero", expression = "java(domain.getGenero().getValue())")
    @Mapping(target = "telefono", expression = "java(domain.getTelefono().getValue())")
    @Mapping(target = "programa", expression = "java(domain.getPrograma().getValue())")
    @Mapping(target = "universidad", expression = "java(domain.getUniversidad().getValue())")
    EstudianteDto toDto(Estudiante domain);
}

