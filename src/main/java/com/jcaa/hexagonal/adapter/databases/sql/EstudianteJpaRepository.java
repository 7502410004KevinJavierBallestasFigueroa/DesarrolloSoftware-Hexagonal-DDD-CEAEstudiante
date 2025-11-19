package com.jcaa.hexagonal.adapter.databases.sql;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EstudianteJpaRepository extends JpaRepository<EstudianteEntity, UUID> {
    
    Optional<EstudianteEntity> findByEmail(String email);
    
    boolean existsByEmail(String email);
}

