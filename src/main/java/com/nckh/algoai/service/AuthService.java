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
import com.nckh.algoai.exception.BadRequestException;
import com.nckh.algoai.exception.NotFoundException;
import com.nckh.algoai.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;


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
            
            LoginReponseDTO response = new LoginReponseDTO();
            response.setToken(token);
            response.setUsername(user.getTenDangNhap());
            response.setEmail(user.getEmail());
            response.setAvatar(user.getAnhDaiDien());
            response.setId(user.getId().toString());
            
            return response;
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            throw new BadRequestException("Email hoặc mật khẩu không chính xác");
        } catch (org.springframework.security.authentication.DisabledException e) {
            throw new BadRequestException("Tài khoản đã bị vô hiệu hóa");
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new BadRequestException("Lỗi xác thực: " + e.getMessage());
        }
    }
} 