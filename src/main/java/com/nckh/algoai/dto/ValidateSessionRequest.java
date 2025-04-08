package com.nckh.algoai.dto;

import lombok.Data;

@Data
public class ValidateSessionRequest {
    private String sessionId;
    private String token;

}
