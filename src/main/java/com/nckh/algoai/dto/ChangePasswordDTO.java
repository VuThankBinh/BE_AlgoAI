package com.nckh.algoai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordDTO {
    @NotBlank(message = "Email không được để trống")
    private String email;
    
    @NotBlank(message = "Mật khẩu hiện tại không được để trống")
    private String matKhauHienTai;
    
    @NotBlank(message = "Mật khẩu mới không được để trống")
    private String matKhauMoi;
    
    @NotBlank(message = "Xác nhận mật khẩu mới không được để trống")
    private String xacNhanMatKhauMoi;
} 