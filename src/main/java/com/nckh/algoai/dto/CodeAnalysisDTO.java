// src/main/java/com/nckh/algoai/dto/CodeAnalysisDTO.java
package com.nckh.algoai.dto;

import lombok.Data;
import java.util.List;

@Data
public class CodeAnalysisDTO {
    private List<String> syntaxErrors;      // Lỗi cú pháp
    private List<String> logicErrors;       // Lỗi logic
    private List<String> styleIssues;       // Vấn đề về phong cách code
    private List<String> performanceIssues; // Vấn đề về hiệu suất
    private String timeComplexity;          // Độ phức tạp thời gian
    private String spaceComplexity;         // Độ phức tạp không gian
    private List<String> bestPractices;     // Các best practices đã áp dụng
    private List<String> improvements;      // Đề xuất cải thiện
}