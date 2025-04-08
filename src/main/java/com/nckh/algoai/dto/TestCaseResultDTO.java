// src/main/java/com/nckh/algoai/dto/TestCaseResultDTO.java
package com.nckh.algoai.dto;

import lombok.Data;

@Data
public class TestCaseResultDTO {
    private String testCase;                // Test case
    private String expectedOutput;          // Đầu ra mong đợi
    private String actualOutput;            // Đầu ra thực tế
    private boolean passed;                 // Kết quả
    private String errorMessage;            // Thông báo lỗi (nếu có)
}