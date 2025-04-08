package com.nckh.algoai.dto;

import lombok.Data;

@Data
public class TestCaseSuggestionRequestDTO {
    private String exerciseDescription;
    private String programmingLanguage;
    private String difficultyLevel;
    private String constraints;
} 