package com.nckh.algoai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nckh.algoai.dto.RegisterDTO;
import com.nckh.algoai.entity.ResponseObject;
import com.nckh.algoai.service.UserService;
import com.nckh.algoai.service.otpService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.nckh.algoai.dto.ChangePasswordDTO;
import com.nckh.algoai.dto.UpdateUserInfoDTO;
import com.nckh.algoai.dto.ResetPasswordDTO;
import com.nckh.algoai.entity.NguoiDungEntity;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "API quản lý người dùng")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private otpService otpService;

    @Operation(summary = "Đăng ký tài khoản", description = "Đăng ký tài khoản mới với thông tin người dùng")
    @ApiResponse(responseCode = "200", description = "Đăng ký thành công")
    @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ")
    @PostMapping("/register")
    public ResponseEntity<ResponseObject<String>> register(@RequestBody RegisterDTO registerDTO) {
        try {
            userService.register(registerDTO);
            return ResponseEntity.ok(
                new ResponseObject<>(
                    HttpStatus.OK,
                    "Đăng ký thành công",
                    ""
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ResponseObject<>(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    ""
                ));
        }
    }

    @Operation(summary = "Đổi mật khẩu", description = "Đổi mật khẩu cho tài khoản người dùng")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Đổi mật khẩu thành công"),
        @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng")
    })
    @PostMapping("/doi-mat-khau")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        NguoiDungEntity updatedUser = userService.changePassword(changePasswordDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Cập nhật thông tin người dùng", description = "Cập nhật thông tin cá nhân của người dùng")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
        @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng")
    })
    @PutMapping("/cap-nhat-thong-tin")
    public ResponseEntity<?> updateUserInfo(@Valid @RequestBody UpdateUserInfoDTO updateUserInfoDTO) {
        NguoiDungEntity updatedUser = userService.updateUserInfo(updateUserInfoDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Gửi OTP đặt lại mật khẩu", description = "Gửi mã OTP đến email để đặt lại mật khẩu")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Gửi OTP thành công"),
        @ApiResponse(responseCode = "400", description = "Email không hợp lệ"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng")
    })
    @PostMapping("/gui-otp-dat-lai-mat-khau")
    public ResponseEntity<?> sendOtpForPasswordReset(
        @Parameter(description = "Email người dùng", required = true)
        @RequestParam String email
    ) {
        otpService.generateAndSendOtp(email);
        return ResponseEntity.ok("Đã gửi OTP đến email của bạn");
    }

    @Operation(summary = "Đặt lại mật khẩu", description = "Đặt lại mật khẩu với mã OTP đã nhận")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Đặt lại mật khẩu thành công"),
        @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng")
    })
    @PostMapping("/dat-lai-mat-khau")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        NguoiDungEntity updatedUser = userService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Lấy thông tin người dùng theo ID", description = "Lấy thông tin chi tiết của người dùng theo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(
        @Parameter(description = "ID người dùng", required = true)
        @PathVariable Integer id
    ) {
        NguoiDungEntity user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Lấy thông tin người dùng theo email", description = "Lấy thông tin chi tiết của người dùng theo email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(
        @Parameter(description = "Email người dùng", required = true)
        @PathVariable String email
    ) {
        NguoiDungEntity user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
} 