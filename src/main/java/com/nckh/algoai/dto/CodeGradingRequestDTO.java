// src/main/java/com/nckh/algoai/dto/CodeGradingRequestDTO.java
package com.nckh.algoai.dto;

import lombok.Data;

@Data
public class CodeGradingRequestDTO {
    private String code;                    // Code cần chấm điểm
    private String exerciseDescription;     // Mô tả bài tập
    private String programmingLanguage;     // Ngôn ngữ lập trình
    private String difficultyLevel;         // Mức độ khó
    private String expectedOutput;          // Đầu ra mong đợi (tùy chọn)
    private String testCases;               // Test cases (tùy chọn)
    private String constraints;             // Các ràng buộc (tùy chọn)
}