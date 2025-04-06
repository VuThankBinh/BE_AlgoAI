package com.nckh.algoai.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Đối tượng yêu cầu gửi OTP")
public class OtpRequest {
    @Schema(description = "Email người dùng", example = "user@example.com", required = true)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    // Getters and setters
} 