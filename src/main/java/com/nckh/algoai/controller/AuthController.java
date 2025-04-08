package com.nckh.algoai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nckh.algoai.dto.LoginDTO;
import com.nckh.algoai.dto.LoginReponseDTO;
import com.nckh.algoai.entity.ResponseObject;
import com.nckh.algoai.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth Controller", description = "API xác thực người dùng")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Đăng nhập", description = "Đăng nhập với email và mật khẩu")
    @ApiResponse(responseCode = "200", description = "Đăng nhập thành công")
    @ApiResponse(responseCode = "401", description = "Thông tin đăng nhập không hợp lệ")
    @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ")
    @PostMapping("/login")
    public ResponseEntity<ResponseObject<LoginReponseDTO>> login( @Valid @RequestBody LoginDTO loginDTO) {
        try {
            LoginReponseDTO response = authService.login(loginDTO);
            return ResponseEntity.ok(
                new ResponseObject<>(
                    org.springframework.http.HttpStatus.OK,
                    "Đăng nhập thành công",
                    response
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED)
                .body(new ResponseObject<>(
                    org.springframework.http.HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    null
                ));
        }
    }
    
    @Operation(summary = "Đăng xuất", description = "Đăng xuất khỏi thiết bị hiện tại")
    @ApiResponse(responseCode = "200", description = "Đăng xuất thành công")
    @ApiResponse(responseCode = "404", description = "Không tìm thấy session")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/logout")
    public ResponseEntity<ResponseObject<Boolean>> logout(
            @Parameter(description = "ID session cần đăng xuất", required = true)
            @RequestParam String sessionId) {
        try {
            boolean result = authService.logout(sessionId);
            if (result) {
                return ResponseEntity.ok(
                    new ResponseObject<>(
                        org.springframework.http.HttpStatus.OK,
                        "Đăng xuất thành công",
                        true
                    )
                );
            } else {
                return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND)
                    .body(new ResponseObject<>(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Không tìm thấy session",
                        false
                    ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject<>(
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lỗi khi đăng xuất: " + e.getMessage(),
                    false
                ));
        }
    }
    
    @Operation(summary = "Đăng xuất tất cả thiết bị", description = "Đăng xuất khỏi tất cả thiết bị")
    @ApiResponse(responseCode = "200", description = "Đăng xuất thành công")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/logout-all")
    public ResponseEntity<ResponseObject<Integer>> logoutAllDevices(
            @Parameter(description = "ID người dùng", required = true)
            @RequestParam Integer userId) {
        try {
            int count = authService.logoutAllDevices(userId);
            return ResponseEntity.ok(
                new ResponseObject<>(
                    org.springframework.http.HttpStatus.OK,
                    "Đã đăng xuất " + count + " thiết bị",
                    count
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject<>(
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lỗi khi đăng xuất: " + e.getMessage(),
                    0
                ));
        }
    }
} 