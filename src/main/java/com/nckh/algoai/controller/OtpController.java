package com.nckh.algoai.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import com.nckh.algoai.dto.OtpRequest;
import org.springframework.http.ResponseEntity;
import com.nckh.algoai.service.otpService;
import com.nckh.algoai.entity.ResponseObject;
import com.nckh.algoai.dto.OtpValidationRequest;

// import java.util.Map;

@RestController
@RequestMapping("/api/otp")
@Tag(name = "OTP Controller", description = "API quản lý xác thực OTP")
public class OtpController {

    @Autowired
    private otpService otpService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Operation(summary = "Gửi OTP", description = "Gửi mã OTP đến email người dùng")
    @ApiResponse(responseCode = "200", description = "OTP đã được gửi thành công")
    @ApiResponse(responseCode = "500", description = "Lỗi server")
    @PostMapping("/send")
    public ResponseEntity<ResponseObject<String>> sendOtp(@RequestBody OtpRequest request) {
        try {
            otpService.generateAndSendOtp(request.getEmail());
            return ResponseEntity.ok(
                    new ResponseObject(
                            HttpStatus.OK,
                            "OTP đã được gửi đến email của bạn",
                            ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ResponseObject(
                                    HttpStatus.INTERNAL_SERVER_ERROR,
                                    "Không thể gửi OTP",
                                    ""));
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping("/sendOTPreset")
    public ResponseEntity<ResponseObject<String>> sendOtpResetPass(@RequestBody OtpRequest request) {
        try {
            otpService.generateAndSendOtpReset(request.getEmail());
            return ResponseEntity.ok(
                    new ResponseObject(
                            HttpStatus.OK,
                            "OTP đã được gửi đến email của bạn",
                            ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ResponseObject(
                                    HttpStatus.INTERNAL_SERVER_ERROR,
                                    "Không thể gửi OTP đến email của bạn",
                                    ""));
        }
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping("/verify")
    public ResponseEntity<ResponseObject<String>> verifyOtp(@RequestBody OtpValidationRequest request) {
        boolean isValid = otpService.validateOtp(request.getEmail(), request.getOtp());

        if (isValid) {
            return ResponseEntity.ok(
                    new ResponseObject(
                            HttpStatus.OK,
                            "Xác thực OTP thành công",
                            ""));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(
                            HttpStatus.BAD_REQUEST,
                            "OTP không hợp lệ hoặc đã hết hạn",
                            ""));
        }
    }
}