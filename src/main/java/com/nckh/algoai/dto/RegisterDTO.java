package com.nckh.algoai.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String tenDangNhap;
    private String email;
    private String matKhau;
    private String xacNhanMatKhau;
    private String otp;
}
