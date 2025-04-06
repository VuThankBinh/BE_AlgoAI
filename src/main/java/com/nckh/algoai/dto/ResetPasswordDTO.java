package com.nckh.algoai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordDTO {
    @NotBlank(message = "Email không được để trống")
    private String email;
    
    @NotBlank(message = "OTP không được để trống")
    private String otp;
    
    @NotBlank(message = "Mật khẩu mới không được để trống")
    private String matKhauMoi;
    
    @NotBlank(message = "Xác nhận mật khẩu mới không được để trống")
    private String xacNhanMatKhauMoi;
} 