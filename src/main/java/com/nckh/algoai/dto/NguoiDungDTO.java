package com.nckh.algoai.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Đối tượng chứa thông tin người dùng")
public class NguoiDungDTO {
    @Schema(description = "ID của người dùng", example = "1")
    private Long id;

    @Schema(description = "Tên đăng nhập", example = "johndoe", required = true)
    private String username;

    @Schema(description = "Địa chỉ email", example = "john.doe@example.com", required = true)
    private String email;

    @Schema(description = "Mật khẩu người dùng", example = "password123", required = true)
    private String password;

    @Schema(description = "Thời gian tạo tài khoản", example = "2024-03-15T10:30:00")
    private LocalDateTime createdDate;

    @Schema(description = "URL ảnh đại diện", example = "https://example.com/avatar.jpg")
    private String avatar;
}  