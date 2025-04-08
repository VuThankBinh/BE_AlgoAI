package com.nckh.algoai.repository;

import com.nckh.algoai.entity.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Integer> {
    
    Optional<UserSessionEntity> findBySessionId(String sessionId);
    
    Optional<UserSessionEntity> findByJwtToken(String jwtToken);
    
    List<UserSessionEntity> findByUserId(Integer userId);
    
    @Query("SELECT s FROM UserSessionEntity s WHERE s.userId = :userId AND s.isActive = true AND s.expiresAt > :now")
    List<UserSessionEntity> findActiveSessionsByUserId(@Param("userId") Integer userId, @Param("now") LocalDateTime now);
    
    @Query("SELECT s FROM UserSessionEntity s WHERE s.isActive = true AND s.expiresAt > :now")
    List<UserSessionEntity> findAllActiveSessions(@Param("now") LocalDateTime now);
    
    @Query("UPDATE UserSessionEntity s SET s.isActive = false WHERE s.expiresAt <= :now")
    void deactivateExpiredSessions(@Param("now") LocalDateTime now);
} 