package com.jcaa.hexagonal.core.port;

import com.jcaa.hexagonal.core.domin.estudiantes.Estudiante;
import com.jcaa.hexagonal.core.domin.estudiantes.EstudianteEmail;
import com.jcaa.hexagonal.core.domin.estudiantes.EstudianteId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepository {
    
    Estudiante save(Estudiante estudiante);
    
    Optional<Estudiante> findById(EstudianteId id);
    
    Optional<Estudiante> findByEmail(EstudianteEmail email);
    
    List<Estudiante> findAll();
    
    Page<Estudiante> findAll(Pageable pageable);
    
    void delete(EstudianteId id);
    
    boolean existsByEmail(EstudianteEmail email);
}

