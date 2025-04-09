package com.nckh.algoai.controller;

import com.nckh.algoai.dto.ExerciseSuggestionRequestDTO;
import com.nckh.algoai.dto.ExerciseSuggestionResponseDTO;
import com.nckh.algoai.service.GeminiExerciseSuggestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercise-suggestion")
@Tag(name = "API tạo bài tập bằng AI", description = "API tạo bài tập bằng AI")
public class ExerciseSuggestionController {

    @Autowired
    private GeminiExerciseSuggestionService exerciseSuggestionService;

    @PostMapping("/suggest")
    @Operation(
        summary = "Tạo bài tập bằng AI",
        description = "Tạo bài tập bằng AI"
    )
    public ResponseEntity<ExerciseSuggestionResponseDTO> suggestExercises(
            @RequestBody ExerciseSuggestionRequestDTO request) {
        ExerciseSuggestionResponseDTO response = exerciseSuggestionService.suggestExercises(request);
        return ResponseEntity.ok(response);
    }
} 