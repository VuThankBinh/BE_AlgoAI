package com.nckh.algoai.dto;

import lombok.Data;

@Data
public class ExerciseSuggestionRequestDTO {
    private String difficultyLevel; // Easy, Medium, Hard
    private String topic; // Tên chủ đề/bài học
    private String theoryInfo; // Thông tin lý thuyết liên quan
    private String exerciseType; // code, quiz, hoặc cả hai
    private String programmingLanguage; // java, python, c++, ...
} 