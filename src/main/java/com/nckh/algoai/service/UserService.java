package com.nckh.algoai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nckh.algoai.dto.RegisterDTO;
import com.nckh.algoai.dto.ChangePasswordDTO;
import com.nckh.algoai.dto.UpdateUserInfoDTO;
import com.nckh.algoai.dto.ResetPasswordDTO;
import com.nckh.algoai.entity.NguoiDungEntity;
import com.nckh.algoai.repository.UserRepository;
import com.nckh.algoai.exception.EmailExistsException;
import com.nckh.algoai.exception.ValidationException;
import com.nckh.algoai.exception.NotFoundException;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private otpService otpService;

    public NguoiDungEntity register(RegisterDTO registerDTO) {
        // Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new EmailExistsException("Email đã được sử dụng");
        }

        // Kiểm tra mật khẩu xác nhận
        if (!registerDTO.getMatKhau().equals(registerDTO.getXacNhanMatKhau())) {
            throw new ValidationException("Mật khẩu xác nhận không khớp");
        }

        // Kiểm tra OTP
        if (!otpService.validateOtp(registerDTO.getEmail(), registerDTO.getOtp())) {
            throw new ValidationException("OTP không hợp lệ hoặc đã hết hạn");
        }

        // Tạo user mới
        NguoiDungEntity user = new NguoiDungEntity();
        user.setTenDangNhap(registerDTO.getTenDangNhap());
        user.setEmail(registerDTO.getEmail());
        user.setMatKhau(passwordEncoder.encode(registerDTO.getMatKhau()));
        user.setNgayTao(LocalDateTime.now());

        // Lưu vào database
        return userRepository.save(user);
    }
    
    /**
     * Đổi mật khẩu cho người dùng
     * @param changePasswordDTO Thông tin đổi mật khẩu
     * @return Người dùng đã được cập nhật
     */
    public NguoiDungEntity changePassword(ChangePasswordDTO changePasswordDTO) {
        // Kiểm tra email tồn tại
        NguoiDungEntity user = userRepository.findByEmail(changePasswordDTO.getEmail());
        if (user == null) {
            throw new NotFoundException("Không tìm thấy người dùng với email: " + changePasswordDTO.getEmail());
        }
        
        // Kiểm tra mật khẩu hiện tại
        if (!passwordEncoder.matches(changePasswordDTO.getMatKhauHienTai(), user.getMatKhau())) {
            throw new ValidationException("Mật khẩu hiện tại không chính xác");
        }
        
        // Kiểm tra mật khẩu mới và xác nhận mật khẩu mới
        if (!changePasswordDTO.getMatKhauMoi().equals(changePasswordDTO.getXacNhanMatKhauMoi())) {
            throw new ValidationException("Mật khẩu mới và xác nhận mật khẩu không khớp");
        }
        
        // Cập nhật mật khẩu mới
        user.setMatKhau(passwordEncoder.encode(changePasswordDTO.getMatKhauMoi()));
        
        // Lưu vào database
        return userRepository.save(user);
    }
    
    /**
     * Cập nhật thông tin người dùng
     * @param updateUserInfoDTO Thông tin cập nhật
     * @return Người dùng đã được cập nhật
     */
    public NguoiDungEntity updateUserInfo(UpdateUserInfoDTO updateUserInfoDTO) {
        // Kiểm tra email tồn tại
        NguoiDungEntity user = userRepository.findByEmail(updateUserInfoDTO.getEmail());
        if (user == null) {
            throw new NotFoundException("Không tìm thấy người dùng với email: " + updateUserInfoDTO.getEmail());
        }
        
        // Cập nhật thông tin
        user.setTenDangNhap(updateUserInfoDTO.getTenDangNhap());
        
        // Cập nhật ảnh đại diện nếu có
        if (updateUserInfoDTO.getAnhDaiDien() != null && !updateUserInfoDTO.getAnhDaiDien().isEmpty()) {
            user.setAnhDaiDien(updateUserInfoDTO.getAnhDaiDien());
        }
        
        // Lưu vào database
        return userRepository.save(user);
    }
    
    /**
     * Đặt lại mật khẩu cho người dùng
     * @param resetPasswordDTO Thông tin đặt lại mật khẩu
     * @return Người dùng đã được cập nhật
     */
    public NguoiDungEntity resetPassword(ResetPasswordDTO resetPasswordDTO) {
        // Kiểm tra email tồn tại
        NguoiDungEntity user = userRepository.findByEmail(resetPasswordDTO.getEmail());
        if (user == null) {
            throw new NotFoundException("Không tìm thấy người dùng với email: " + resetPasswordDTO.getEmail());
        }
        
        // Kiểm tra OTP
        if (!otpService.validateOtp(resetPasswordDTO.getEmail(), resetPasswordDTO.getOtp())) {
            throw new ValidationException("OTP không hợp lệ hoặc đã hết hạn");
        }
        
        // Kiểm tra mật khẩu mới và xác nhận mật khẩu mới
        if (!resetPasswordDTO.getMatKhauMoi().equals(resetPasswordDTO.getXacNhanMatKhauMoi())) {
            throw new ValidationException("Mật khẩu mới và xác nhận mật khẩu không khớp");
        }
        
        // Cập nhật mật khẩu mới
        user.setMatKhau(passwordEncoder.encode(resetPasswordDTO.getMatKhauMoi()));
        
        // Lưu vào database
        return userRepository.save(user);
    }
    
    /**
     * Lấy thông tin người dùng theo ID
     * @param id ID người dùng
     * @return Người dùng
     */
    public NguoiDungEntity getUserById(Integer id) {
        Optional<NguoiDungEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Không tìm thấy người dùng với ID: " + id);
        }
        return userOptional.get();
    }
    
    /**
     * Lấy thông tin người dùng theo email
     * @param email Email người dùng
     * @return Người dùng
     */
    public NguoiDungEntity getUserByEmail(String email) {
        NguoiDungEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("Không tìm thấy người dùng với email: " + email);
        }
        return user;
    }
} 