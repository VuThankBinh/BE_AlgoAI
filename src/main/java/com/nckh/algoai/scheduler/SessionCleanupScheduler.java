package com.nckh.algoai.scheduler;

import com.nckh.algoai.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SessionCleanupScheduler {

    private static final Logger logger = Logger.getLogger(SessionCleanupScheduler.class.getName());

    @Autowired
    private UserSessionService userSessionService;

    /**
     * Chạy mỗi giờ một lần để vô hiệu hóa các session đã hết hạn
     */
    @Scheduled(fixedRate = 3600000) // 1 giờ = 3600000 milliseconds
    public void cleanupExpiredSessions() {
        try {
            int count = userSessionService.deactivateExpiredSessions();
            logger.info("Đã vô hiệu hóa " + count + " session đã hết hạn");
        } catch (Exception e) {
            logger.severe("Lỗi khi vô hiệu hóa session: " + e.getMessage());
        }
    }
} 