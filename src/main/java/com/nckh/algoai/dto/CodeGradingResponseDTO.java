// src/main/java/com/nckh/algoai/dto/CodeGradingResponseDTO.java
package com.nckh.algoai.dto;

import lombok.Data;
import java.util.List;

@Data
public class CodeGradingResponseDTO {
    private int totalScore;                 // Tổng điểm
    private int accuracyScore;              // Điểm độ chính xác
    private int structureScore;             // Điểm cấu trúc code
    private int edgeCasesScore;             // Điểm xử lý trường hợp đặc biệt
    private int performanceScore;           // Điểm hiệu suất
    private String feedback;                // Nhận xét chi tiết
    private List<String> suggestions;       // Gợi ý cải thiện
    private String errorMessage;            // Thông báo lỗi (nếu có)
    private List<TestCaseResultDTO> testCaseResults; // Kết quả test cases
    private CodeAnalysisDTO codeAnalysis;   // Phân tích code chi tiết

    public CodeGradingResponseDTO() {}

    public CodeGradingResponseDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}