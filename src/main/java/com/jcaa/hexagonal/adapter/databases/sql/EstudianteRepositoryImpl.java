package com.jcaa.hexagonal.adapter.databases.sql;

import com.jcaa.hexagonal.adapter.databases.sql.mappers.EstudianteEntityMapper;
import com.jcaa.hexagonal.core.domin.estudiantes.Estudiante;
import com.jcaa.hexagonal.core.domin.estudiantes.EstudianteEmail;
import com.jcaa.hexagonal.core.domin.estudiantes.EstudianteId;
import com.jcaa.hexagonal.core.port.EstudianteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EstudianteRepositoryImpl implements EstudianteRepository {
    
    private final EstudianteJpaRepository jpaRepository;
    private final EstudianteEntityMapper mapper;
    
    public EstudianteRepositoryImpl(EstudianteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
        this.mapper = EstudianteEntityMapper.INSTANCE;
    }
    
    @Override
    public Estudiante save(Estudiante estudiante) {
        EstudianteEntity entity;
        
        if (estudiante.getId() != null && jpaRepository.existsById(estudiante.getId().getValue())) {
            entity = jpaRepository.findById(estudiante.getId().getValue())
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
            LocalDateTime originalCreatedAt = entity.getCreatedAt();
            mapper.updateEntity(estudiante, entity);
            entity.setCreatedAt(originalCreatedAt);
        } else {
            entity = mapper.toEntity(estudiante);
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(LocalDateTime.now());
            }
        }
        
        EstudianteEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Estudiante> findById(EstudianteId id) {
        return jpaRepository.findById(id.getValue())
                .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Estudiante> findByEmail(EstudianteEmail email) {
        return jpaRepository.findByEmail(email.getValue())
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Estudiante> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<Estudiante> findAll(Pageable pageable) {
        Page<EstudianteEntity> entities = jpaRepository.findAll(pageable);
        return entities.map(mapper::toDomain);
    }
    
    @Override
    public void delete(EstudianteId id) {
        jpaRepository.deleteById(id.getValue());
    }
    
    @Override
    public boolean existsByEmail(EstudianteEmail email) {
        return jpaRepository.existsByEmail(email.getValue());
    }
}

