// src/main/java/com/nckh/algoai/controller/CodeGradingController.java
package com.nckh.algoai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nckh.algoai.service.GeminiCodeGradingService;
import com.nckh.algoai.dto.CodeGradingRequestDTO;
import com.nckh.algoai.dto.CodeGradingResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/code-grading")
@Tag(name = "Code Grading Controller", description = "API chấm điểm code")
public class CodeGradingController {

    @Autowired
    private GeminiCodeGradingService geminiCodeGradingService;

    @PostMapping("/grade")
    public ResponseEntity<CodeGradingResponseDTO> gradeCode(
            @RequestBody CodeGradingRequestDTO request) {
        try {
            CodeGradingResponseDTO response = geminiCodeGradingService.gradeCode(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(new CodeGradingResponseDTO("Lỗi khi chấm điểm: " + e.getMessage()));
        }
    }
}