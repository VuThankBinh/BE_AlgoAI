package com.nckh.algoai.controller;

import com.nckh.algoai.dto.TestCaseSuggestionRequestDTO;
import com.nckh.algoai.dto.TestCaseSuggestionResponseDTO;
import com.nckh.algoai.service.GeminiTestCaseSuggestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-case-suggestion")
@Tag(name = "API tạo test case", description = "API tạo test case")
public class TestCaseSuggestionController {

    @Autowired
    private GeminiTestCaseSuggestionService testCaseSuggestionService;

    @PostMapping("/suggest")
    @Operation(
        summary = "Tạo test case",
        description = "Tạo test case"
    )
    public ResponseEntity<TestCaseSuggestionResponseDTO> suggestTestCases(
            @RequestBody TestCaseSuggestionRequestDTO request) {
        TestCaseSuggestionResponseDTO response = testCaseSuggestionService.suggestTestCases(request);
        return ResponseEntity.ok(response);
    }
} 