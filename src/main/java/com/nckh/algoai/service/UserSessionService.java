package com.nckh.algoai.service;

import com.nckh.algoai.entity.UserSessionEntity;
import com.nckh.algoai.repository.UserSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserSessionService {

    private static final Logger logger = LoggerFactory.getLogger(UserSessionService.class);

    @Autowired
    private UserSessionRepository userSessionRepository;
    
    /**
     * Tạo session mới cho người dùng
     * @param userId ID người dùng
     * @param jwtToken Token JWT
     * @param expiresAt Thời gian hết hạn
     * @return Session đã tạo
     */
    @Transactional
    public UserSessionEntity createSession(Integer userId, String jwtToken, LocalDateTime expiresAt) {
        // Tạo sessionId mới
        String sessionId = "session-" + UUID.randomUUID().toString();
        
        // Tạo session mới
        UserSessionEntity session = new UserSessionEntity();
        session.setUserId(userId);
        session.setSessionId(sessionId);
        session.setJwtToken(jwtToken);
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(expiresAt);
        
        // Lưu vào database
        return userSessionRepository.save(session);
    }
    
    /**
     * Lấy session theo sessionId
     * @param sessionId ID session
     * @return Session nếu tìm thấy
     */
    public Optional<UserSessionEntity> getSessionBySessionId(String sessionId) {
        return userSessionRepository.findBySessionId(sessionId);
    }
    
    /**
     * Lấy session theo JWT token
     * @param jwtToken Token JWT
     * @return Session nếu tìm thấy
     */
    public Optional<UserSessionEntity> getSessionByJwtToken(String jwtToken) {
        return userSessionRepository.findByJwtToken(jwtToken);
    }
    
    /**
     * Lấy tất cả session đang hoạt động của người dùng
     * @param userId ID người dùng
     * @return Danh sách session
     */
    public List<UserSessionEntity> getActiveSessionsByUserId(Integer userId) {
        return userSessionRepository.findActiveSessionsByUserId(userId, LocalDateTime.now());
    }
    
    /**
     * Vô hiệu hóa session
     * @param sessionId ID session
     * @return true nếu thành công, false nếu không tìm thấy session
     */
    @Transactional
    public boolean deactivateSession(String sessionId) {
        Optional<UserSessionEntity> sessionOpt = userSessionRepository.findBySessionId(sessionId);
        if (sessionOpt.isPresent()) {
            UserSessionEntity session = sessionOpt.get();
            session.setIsActive(false);
            userSessionRepository.save(session);
            return true;
        }
        return false;
    }
    
    /**
     * Vô hiệu hóa tất cả session của người dùng
     * @param userId ID người dùng
     * @return Số lượng session đã vô hiệu hóa
     */
    @Transactional
    public int deactivateAllSessionsByUserId(Integer userId) {
        List<UserSessionEntity> sessions = userSessionRepository.findByUserId(userId);
        for (UserSessionEntity session : sessions) {
            session.setIsActive(false);
        }
        userSessionRepository.saveAll(sessions);
        return sessions.size();
    }
    
    /**
     * Vô hiệu hóa tất cả session đã hết hạn
     * @return Số lượng session đã vô hiệu hóa
     */
    @Transactional
    public int deactivateExpiredSessions() {
        LocalDateTime now = LocalDateTime.now();
        List<UserSessionEntity> expiredSessions = userSessionRepository.findAll().stream()
                .filter(session -> session.getIsActive() && session.getExpiresAt().isBefore(now))
                .toList();
        
        for (UserSessionEntity session : expiredSessions) {
            session.setIsActive(false);
        }
        userSessionRepository.saveAll(expiredSessions);
        return expiredSessions.size();
    }

    /**
     * Kiểm tra và xác thực session
     * @param sessionId ID session cần kiểm tra
     * @return true nếu session hợp lệ, false nếu session không hợp lệ hoặc đã hết hạn
     */
    public boolean validateSession(String sessionId) {
        try {
            Optional<UserSessionEntity> sessionOpt = userSessionRepository.findBySessionId(sessionId);
            if (sessionOpt.isEmpty()) {
                return false;
            }

            UserSessionEntity session = sessionOpt.get();
            LocalDateTime now = LocalDateTime.now();

            // Kiểm tra session đã hết hạn chưa
            if (session.getExpiresAt().isBefore(now)) {
                // Vô hiệu hóa session đã hết hạn
                session.setIsActive(false);
                userSessionRepository.save(session);
                return false;
            }

            // Kiểm tra session có đang active không
            if (!session.getIsActive()) {
                return false;
            }

            return true;
        } catch (Exception e) {
            logger.error("Error validating session: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Kiểm tra và xác thực token
     * @param jwtToken Token JWT cần kiểm tra
     * @return true nếu token hợp lệ, false nếu token không hợp lệ hoặc đã hết hạn
     */
    public boolean validateToken(String jwtToken) {
        try {
            Optional<UserSessionEntity> sessionOpt = userSessionRepository.findByJwtToken(jwtToken);
            if (sessionOpt.isEmpty()) {
                return false;
            }

            UserSessionEntity session = sessionOpt.get();
            LocalDateTime now = LocalDateTime.now();

            // Kiểm tra token đã hết hạn chưa
            if (session.getExpiresAt().isBefore(now)) {
                // Vô hiệu hóa session đã hết hạn
                session.setIsActive(false);
                userSessionRepository.save(session);
                return false;
            }

            // Kiểm tra session có đang active không
            if (!session.getIsActive()) {
                return false;
            }

            return true;
        } catch (Exception e) {
            logger.error("Error validating token: {}", e.getMessage(), e);
            return false;
        }
    }
} 