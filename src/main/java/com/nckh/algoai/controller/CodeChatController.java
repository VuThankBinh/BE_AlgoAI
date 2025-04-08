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
@RequestMapping("/api/code-chat")
@Tag(name = "Code Chat Controller", description = "API xử lý chat liên quan đến bài tập code")
public class CodeChatController {

    @Autowired
    private GeminiService geminiService;

    @Operation(summary = "Hỏi về lý thuyết lập trình", description = "Đặt câu hỏi về lý thuyết lập trình")
    @PostMapping("/theory")
    public ResponseEntity<String> askAboutTheory(
            @RequestBody ChatQuestionDTO chatQuestionDTO) {
        
        // Nếu không có sessionId, tạo mới
        String currentSessionId = chatQuestionDTO.getSessionId() != null && !chatQuestionDTO.getSessionId().isEmpty() 
                ? chatQuestionDTO.getSessionId() 
                : "theory-" + UUID.randomUUID().toString();
        
        // Thêm hướng dẫn cho Gemini để chỉ trả lời về lý thuyết
        String enhancedQuestion = "Hãy giải thích về lý thuyết liên quan đến: " + chatQuestionDTO.getQuestion() + 
                                 ". Chỉ cung cấp giải thích lý thuyết, không cung cấp code đầy đủ.";
        
        // Gửi câu hỏi đến Gemini
        String response = geminiService.generateResponse(enhancedQuestion, currentSessionId);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Hỏi về thuật toán", description = "Đặt câu hỏi về thuật toán")
    @PostMapping("/algorithm")
    public ResponseEntity<String> askAboutAlgorithm(
            @RequestBody ChatQuestionDTO chatQuestionDTO) {
        
        // Nếu không có sessionId, tạo mới
        String currentSessionId = chatQuestionDTO.getSessionId() != null && !chatQuestionDTO.getSessionId().isEmpty() 
                ? chatQuestionDTO.getSessionId() 
                : "algorithm-" + UUID.randomUUID().toString();
        
        // Thêm hướng dẫn cho Gemini để chỉ trả lời về thuật toán
        String enhancedQuestion = "Hãy giải thích về thuật toán liên quan đến: " + chatQuestionDTO.getQuestion() + 
                                 ". Chỉ cung cấp giải thích về thuật toán, không cung cấp code đầy đủ.";
        
        // Gửi câu hỏi đến Gemini
        String response = geminiService.generateResponse(enhancedQuestion, currentSessionId);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Hỏi về hướng dẫn lập trình", description = "Đặt câu hỏi về hướng dẫn lập trình")
    @PostMapping("/guide")
    public ResponseEntity<String> askAboutGuide(
            @RequestBody ChatQuestionDTO chatQuestionDTO) {
        
        // Nếu không có sessionId, tạo mới
        String currentSessionId = chatQuestionDTO.getSessionId() != null && !chatQuestionDTO.getSessionId().isEmpty() 
                ? chatQuestionDTO.getSessionId() 
                : "guide-" + UUID.randomUUID().toString();
        
        // Thêm hướng dẫn cho Gemini để chỉ trả lời về hướng dẫn lập trình
        String enhancedQuestion = "Hãy cung cấp hướng dẫn về: " + chatQuestionDTO.getQuestion() + 
                                 ". Chỉ cung cấp hướng dẫn từng bước, không cung cấp code đầy đủ.";
        
        // Gửi câu hỏi đến Gemini
        String response = geminiService.generateResponse(enhancedQuestion, currentSessionId);
        
        return ResponseEntity.ok(response);
    }
} 