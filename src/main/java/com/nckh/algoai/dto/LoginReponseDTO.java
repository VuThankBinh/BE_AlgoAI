package com.nckh.algoai.dto;

import lombok.Data;

@Data
public class LoginReponseDTO {
    private String token;
    private String refreshToken;
    private String username;
    private String email;
    private String avatar;
    private String id;
    private String sessionId;
}
