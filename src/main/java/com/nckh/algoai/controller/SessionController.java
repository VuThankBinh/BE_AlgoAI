package com.nckh.algoai.controller;

import com.nckh.algoai.dto.ValidateSessionRequest;
import com.nckh.algoai.entity.ResponseObject;
import com.nckh.algoai.entity.UserSessionEntity;
import com.nckh.algoai.service.UserSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@Tag(name = "Session Controller", description = "API quản lý session")
public class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private UserSessionService userSessionService;

    @Operation(summary = "Lấy danh sách session", description = "Lấy danh sách tất cả session đang hoạt động của người dùng")
    @ApiResponse(responseCode = "200", description = "Lấy danh sách session thành công")
    @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    @GetMapping
    public ResponseEntity<ResponseObject<List<UserSessionEntity>>> getActiveSessions(
            @Parameter(description = "ID người dùng", required = true)
            @RequestParam Integer userId,
            HttpServletRequest request) {
        try {
            // Kiểm tra quyền truy cập
            Integer currentUserId = (Integer) request.getAttribute("userId");
            if (currentUserId == null || !currentUserId.equals(userId)) {
                logger.warn("Unauthorized access attempt. Current user: {}, Requested user: {}", currentUserId, userId);
                return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN)
                    .body(new ResponseObject<>(
                        org.springframework.http.HttpStatus.FORBIDDEN,
                        "Bạn không có quyền truy cập thông tin session của người dùng khác",
                        null
                    ));
            }

            List<UserSessionEntity> sessions = userSessionService.getActiveSessionsByUserId(userId);
            return ResponseEntity.ok(
                new ResponseObject<>(
                    org.springframework.http.HttpStatus.OK,
                    "Lấy danh sách session thành công",
                    sessions
                )
            );
        } catch (Exception e) {
            logger.error("Error getting active sessions: {}", e.getMessage(), e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject<>(
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lỗi khi lấy danh sách session: " + e.getMessage(),
                    null
                ));
        }
    }

    @Operation(summary = "Vô hiệu hóa session", description = "Vô hiệu hóa một session cụ thể")
    @ApiResponse(responseCode = "200", description = "Vô hiệu hóa session thành công")
    @ApiResponse(responseCode = "404", description = "Không tìm thấy session")
    @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<ResponseObject<Boolean>> deactivateSession(
            @Parameter(description = "ID session cần vô hiệu hóa", required = true)
            @PathVariable String sessionId,
            HttpServletRequest request) {
        try {
            // Kiểm tra quyền truy cập
            Integer currentUserId = (Integer) request.getAttribute("userId");
            UserSessionEntity session = userSessionService.getSessionBySessionId(sessionId)
                .orElse(null);

            if (session == null) {
                return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND)
                    .body(new ResponseObject<>(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Không tìm thấy session",
                        false
                    ));
            }

            if (!session.getUserId().equals(currentUserId)) {
                logger.warn("Unauthorized session deactivation attempt. Current user: {}, Session user: {}", 
                    currentUserId, session.getUserId());
                return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN)
                    .body(new ResponseObject<>(
                        org.springframework.http.HttpStatus.FORBIDDEN,
                        "Bạn không có quyền vô hiệu hóa session của người dùng khác",
                        false
                    ));
            }

            boolean result = userSessionService.deactivateSession(sessionId);
            return ResponseEntity.ok(
                new ResponseObject<>(
                    org.springframework.http.HttpStatus.OK,
                    "Vô hiệu hóa session thành công",
                    result
                )
            );
        } catch (Exception e) {
            logger.error("Error deactivating session: {}", e.getMessage(), e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject<>(
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lỗi khi vô hiệu hóa session: " + e.getMessage(),
                    false
                ));
        }
    }

    @Operation(summary = "Vô hiệu hóa tất cả session", description = "Vô hiệu hóa tất cả session của người dùng")
    @ApiResponse(responseCode = "200", description = "Vô hiệu hóa tất cả session thành công")
    @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ResponseObject<Integer>> deactivateAllSessions(
            @Parameter(description = "ID người dùng", required = true)
            @PathVariable Integer userId,
            HttpServletRequest request) {
        try {
            // Kiểm tra quyền truy cập
            Integer currentUserId = (Integer) request.getAttribute("userId");
            if (currentUserId == null || !currentUserId.equals(userId)) {
                logger.warn("Unauthorized access attempt. Current user: {}, Requested user: {}", currentUserId, userId);
                return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN)
                    .body(new ResponseObject<>(
                        org.springframework.http.HttpStatus.FORBIDDEN,
                        "Bạn không có quyền vô hiệu hóa session của người dùng khác",
                        0
                    ));
            }

            int count = userSessionService.deactivateAllSessionsByUserId(userId);
            return ResponseEntity.ok(
                new ResponseObject<>(
                    org.springframework.http.HttpStatus.OK,
                    "Đã vô hiệu hóa " + count + " session",
                    count
                )
            );
        } catch (Exception e) {
            logger.error("Error deactivating all sessions: {}", e.getMessage(), e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject<>(
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lỗi khi vô hiệu hóa tất cả session: " + e.getMessage(),
                    0
                ));
        }
    }

    @Operation(summary = "Kiểm tra trạng thái session và token", description = "Kiểm tra xem session và token có còn hợp lệ không")
    @ApiResponse(responseCode = "200", description = "Kiểm tra thành công")
    @ApiResponse(responseCode = "401", description = "Session hoặc token không hợp lệ")
    @GetMapping("/validate")
    public ResponseEntity<ResponseObject<Boolean>> validateSessionAndToken(
            @RequestBody ValidateSessionRequest request) {
        try {

            

            // Kiểm tra session và token
            boolean isSessionValid = userSessionService.validateSession(request.getSessionId());
            boolean isTokenValid = userSessionService.validateToken(request.getToken());

            if (!isSessionValid || !isTokenValid) {
                return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject<>(
                        org.springframework.http.HttpStatus.UNAUTHORIZED,
                        "Session hoặc token không hợp lệ hoặc đã hết hạn",
                        false
                    ));
            }

            return ResponseEntity.ok(
                new ResponseObject<>(
                    org.springframework.http.HttpStatus.OK,
                    "Session và token hợp lệ",
                    true
                )
            );
        } catch (Exception e) {
            logger.error("Error validating session and token: {}", e.getMessage(), e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject<>(
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lỗi khi kiểm tra session và token: " + e.getMessage(),
                    false
                ));
        }
    }
} 