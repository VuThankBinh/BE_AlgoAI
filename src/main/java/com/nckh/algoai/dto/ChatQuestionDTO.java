package com.nckh.algoai.dto;

import lombok.Data;

@Data
public class ChatQuestionDTO {

    private String question;
    private String sessionId;
    private Integer exerciseId;
}
