package com.nckh.algoai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.nckh.algoai.dto.LoginDTO;
import com.nckh.algoai.dto.LoginReponseDTO;
import com.nckh.algoai.entity.NguoiDungEntity;
import com.nckh.algoai.entity.UserSessionEntity;
import com.nckh.algoai.exception.BadRequestException;
import com.nckh.algoai.exception.NotFoundException;
import com.nckh.algoai.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserSessionService userSessionService;


    public LoginReponseDTO login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(),
                    loginDTO.getMatKhau()
                )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);
            if(userDetails == null){
                throw new NotFoundException("User not found");
            }
            
            NguoiDungEntity user = userRepository.findByEmail(loginDTO.getEmail());
            
            // Tạo session mới cho người dùng
            LocalDateTime expiresAt = LocalDateTime.now().plusDays(7); // Session hết hạn sau 7 ngày
            UserSessionEntity session = userSessionService.createSession(user.getId(), token, expiresAt);
            
            LoginReponseDTO response = new LoginReponseDTO();
            response.setToken(token);
            response.setUsername(user.getTenDangNhap());
            response.setEmail(user.getEmail());
            response.setAvatar(user.getAnhDaiDien());
            response.setId(user.getId().toString());
            response.setSessionId(session.getSessionId()); // Thêm sessionId vào response
            
            return response;
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            throw new BadRequestException("Email hoặc mật khẩu không chính xác");
        } catch (org.springframework.security.authentication.DisabledException e) {
            throw new BadRequestException("Tài khoản đã bị vô hiệu hóa");
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new BadRequestException("Lỗi xác thực: " + e.getMessage());
        }
    }
    
    /**
     * Đăng xuất người dùng
     * @param sessionId ID session cần đăng xuất
     * @return true nếu thành công, false nếu không tìm thấy session
     */
    public boolean logout(String sessionId) {
        return userSessionService.deactivateSession(sessionId);
    }
    
    /**
     * Đăng xuất tất cả thiết bị của người dùng
     * @param userId ID người dùng
     * @return Số lượng session đã đăng xuất
     */
    public int logoutAllDevices(Integer userId) {
        return userSessionService.deactivateAllSessionsByUserId(userId);
    }
} 