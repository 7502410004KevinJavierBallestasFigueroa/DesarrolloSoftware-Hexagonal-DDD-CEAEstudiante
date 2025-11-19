package com.jcaa.hexagonal.adapter.databases.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenBlacklistJpaRepository extends JpaRepository<TokenBlacklistEntity, String> {
    boolean existsByToken(String token);
    void deleteByExpirationTimeBefore(LocalDateTime now);
}

