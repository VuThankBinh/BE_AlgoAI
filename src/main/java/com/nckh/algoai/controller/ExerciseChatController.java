package com.nckh.algoai.controller;

import com.nckh.algoai.dto.ChatQuestionDTO;
import com.nckh.algoai.service.GeminiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/exercise-chat")
@Tag(name = "Exercise Chat Controller", description = "API xử lý chat liên quan đến bài tập cụ thể")
public class ExerciseChatController {

    @Autowired
    private GeminiService geminiService;

    @Operation(summary = "Hỏi về bài tập code", description = "Đặt câu hỏi về bài tập code cụ thể")
    @PostMapping("/code-exercise")
    public ResponseEntity<String> askAboutCodeExercise(
            @RequestBody ChatQuestionDTO chatQuestionDTO) {
        
        // Nếu không có sessionId, tạo mới
        String currentSessionId = chatQuestionDTO.getSessionId() != null && !chatQuestionDTO.getSessionId().isEmpty() 
                ? chatQuestionDTO.getSessionId() 
                : "exercise-" + chatQuestionDTO.getExerciseId() + "-" + UUID.randomUUID().toString();
        
        // Thêm hướng dẫn cho Gemini để chỉ trả lời về bài tập cụ thể
        String enhancedQuestion = "Tôi đang làm bài tập code có ID: " + chatQuestionDTO.getExerciseId() + 
                                 ". Câu hỏi của tôi là: " + chatQuestionDTO.getQuestion() + 
                                 ". Hãy giúp tôi hiểu bài tập này và cung cấp hướng dẫn, nhưng không cung cấp code đầy đủ.";
        
        // Gửi câu hỏi đến Gemini
        String response = geminiService.generateResponse(enhancedQuestion, currentSessionId);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Hỏi về bài tập quiz", description = "Đặt câu hỏi về bài tập quiz cụ thể")
    @PostMapping("/quiz-exercise")
    public ResponseEntity<String> askAboutQuizExercise(
            @RequestBody ChatQuestionDTO chatQuestionDTO) {
        
        // Nếu không có sessionId, tạo mới
        String currentSessionId = chatQuestionDTO.getSessionId() != null && !chatQuestionDTO.getSessionId().isEmpty() 
                ? chatQuestionDTO.getSessionId() 
                : "quiz-" + chatQuestionDTO.getExerciseId() + "-" + UUID.randomUUID().toString();
        
        // Thêm hướng dẫn cho Gemini để chỉ trả lời về bài tập quiz cụ thể
        String enhancedQuestion = "Tôi đang làm bài tập quiz có ID: " + chatQuestionDTO.getExerciseId() + 
                                 ". Câu hỏi của tôi là: " + chatQuestionDTO.getQuestion() + 
                                 ". Hãy giúp tôi hiểu bài tập này và cung cấp hướng dẫn, nhưng không cung cấp đáp án trực tiếp.";
        
        // Gửi câu hỏi đến Gemini
        String response = geminiService.generateResponse(enhancedQuestion, currentSessionId);
        
        return ResponseEntity.ok(response);
    }
} 