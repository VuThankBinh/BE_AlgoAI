package com.nckh.algoai.controller;

import com.nckh.algoai.model.ChatMessage;
import com.nckh.algoai.service.ChatbotService;
import com.nckh.algoai.service.GeminiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chat Controller", description = "API xử lý chat với Gemini")
public class ChatController {

    @Autowired
    private ChatbotService chatbotService;

    @Autowired
    private GeminiService geminiService;

    @Operation(summary = "Gửi tin nhắn và nhận phản hồi", description = "Gửi tin nhắn đến Gemini và nhận phản hồi")
    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(
            @Parameter(description = "Nội dung tin nhắn", required = true)
            @RequestParam String message,
            @Parameter(description = "ID phiên chat (nếu không có sẽ tạo mới)")
            @RequestParam(required = false) String sessionId) {
        
        // Nếu không có sessionId, tạo mới
        String currentSessionId = sessionId != null && !sessionId.isEmpty() 
                ? sessionId 
                : "session-" + UUID.randomUUID().toString();
        
        // Xử lý tin nhắn và tạo phản hồi
        ChatMessage response = chatbotService.processMessage(message, currentSessionId);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Lấy lịch sử chat", description = "Lấy lịch sử chat của một phiên")
    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory(
            @Parameter(description = "ID phiên chat", required = true)
            @RequestParam String sessionId) {
        
        // Lấy lịch sử chat từ repository
        List<ChatMessage> history = chatbotService.getChatHistory(sessionId);
        
        return ResponseEntity.ok(history);
    }

    @Operation(summary = "Xóa lịch sử chat", description = "Xóa lịch sử chat của một phiên")
    @DeleteMapping("/history")
    public ResponseEntity<String> clearChatHistory(
            @Parameter(description = "ID phiên chat", required = true)
            @RequestParam String sessionId) {
        
        // Xóa lịch sử chat
        geminiService.clearChatHistory(sessionId);
        
        return ResponseEntity.ok("Đã xóa lịch sử chat thành công");
    }
} 