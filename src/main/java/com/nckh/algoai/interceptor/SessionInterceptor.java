package com.nckh.algoai.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nckh.algoai.entity.ResponseObject;
import com.nckh.algoai.entity.UserSessionEntity;
import com.nckh.algoai.service.UserSessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    private static final List<String> PUBLIC_ENDPOINTS = Arrays.asList(
        "/api/auth/login",
        "/api/auth/register",
        "/api/auth/refresh-token",
        "/swagger-ui",
        "/v3/api-docs"
    );

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        logger.info("Intercepting request to: {}", requestURI);

        // Kiểm tra nếu là endpoint public
        if (isPublicEndpoint(requestURI)) {
            logger.info("Public endpoint detected, allowing access");
            return true;
        }

        // Lấy session ID từ header
        String sessionId = request.getHeader("X-Session-ID");
        logger.info("Session ID from header: {}", sessionId);

        if (sessionId == null || sessionId.isEmpty()) {
            logger.warn("No session ID provided");
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Không tìm thấy session ID");
            return false;
        }

        try {
            // Kiểm tra session
            UserSessionEntity session = userSessionService.getSessionBySessionId(sessionId)
                .orElse(null);

            if (session == null) {
                logger.warn("Session not found: {}", sessionId);
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Session không tồn tại");
                return false;
            }

            if (!session.getIsActive()) {
                logger.warn("Session is inactive: {}", sessionId);
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Session đã bị vô hiệu hóa");
                return false;
            }

            if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
                logger.warn("Session has expired: {}", sessionId);
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Session đã hết hạn");
                return false;
            }

            // Thêm thông tin user vào request
            request.setAttribute("userId", session.getUserId());
            logger.info("Session validated successfully for user: {}", session.getUserId());
            return true;

        } catch (Exception e) {
            logger.error("Error validating session: {}", e.getMessage(), e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xác thực session: " + e.getMessage());
            return false;
        }
    }

    private boolean isPublicEndpoint(String requestURI) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(requestURI::startsWith);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        ResponseObject<?> errorResponse = new ResponseObject<>(
            org.springframework.http.HttpStatus.valueOf(status),
            message,
            null
        );
        
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
} 