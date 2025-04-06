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
} 