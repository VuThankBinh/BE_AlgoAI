package com.nckh.algoai.dto;

import lombok.Data;
import java.util.List;

@Data
public class TestCaseSuggestionResponseDTO {
    private List<String> testCases;
    private List<String> edgeCases;
    private String expectedOutput;
    private String errorMessage;
} 