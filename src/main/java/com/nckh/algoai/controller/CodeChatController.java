package com.nckh.algoai.controller;

import com.nckh.algoai.dto.ChatQuestionDTO;
import com.nckh.algoai.dto.DebugQuestionDTO;
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
        
        // Thêm hướng dẫn cho Gemini để chỉ trả lời về lý thuyết một cách ngắn gọn
        String enhancedQuestion = "Hãy giải thích ngắn gọn về lý thuyết: " + chatQuestionDTO.getQuestion() + 
                                 ". Chỉ cung cấp các bước thực hiện và độ phức tạp nếu có. KHÔNG đưa ra ví dụ hoặc code minh họa. KHÔNG giải thích chi tiết. Trả lời càng ngắn gọn càng tốt.";
        
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
        
        // Thêm hướng dẫn cho Gemini để chỉ trả lời về các bước thực hiện thuật toán
        String enhancedQuestion = "Hãy liệt kê ngắn gọn các bước thực hiện của thuật toán: " + chatQuestionDTO.getQuestion() + 
                                 ". CHỈ liệt kê các bước chính và độ phức tạp. KHÔNG đưa ra mã giả hoặc code. KHÔNG giải thích chi tiết. KHÔNG đưa ra ví dụ. Trả lời càng ngắn gọn càng tốt.";
        
        // Gửi câu hỏi đến Gemini
        String response = geminiService.generateResponse(enhancedQuestion, currentSessionId);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Hỏi về mã giả của thuật toán", description = "Đặt câu hỏi để lấy mã giả của thuật toán")
    @PostMapping("/guide")
    public ResponseEntity<String> askAboutGuide(
            @RequestBody ChatQuestionDTO chatQuestionDTO) {
        
        // Nếu không có sessionId, tạo mới
        String currentSessionId = chatQuestionDTO.getSessionId() != null && !chatQuestionDTO.getSessionId().isEmpty() 
                ? chatQuestionDTO.getSessionId() 
                : "guide-" + UUID.randomUUID().toString();
        
        // Thêm hướng dẫn cho Gemini để chỉ trả về mã giả dạng code
        String enhancedQuestion = "Viết mã giả (pseudocode) cho: " + chatQuestionDTO.getQuestion() + 
                                 ". CHỈ viết mã giả dạng code. KHÔNG giải thích. KHÔNG mô tả. KHÔNG đưa ra ví dụ. KHÔNG đưa ra code thật. Trả lời theo định dạng sau:\n```\n<mã giả của bạn>\n```";
        
        // Gửi câu hỏi đến Gemini
        String response = geminiService.generateResponse(enhancedQuestion, currentSessionId);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Gợi ý sửa lỗi code", description = "Đưa ra code lỗi và thông báo lỗi để nhận gợi ý sửa")
    @PostMapping("/debug")
    public ResponseEntity<String> debugCode(
            @RequestBody DebugQuestionDTO debugQuestionDTO) {
        
        // Nếu không có sessionId, tạo mới
        String currentSessionId = debugQuestionDTO.getSessionId() != null && !debugQuestionDTO.getSessionId().isEmpty() 
                ? debugQuestionDTO.getSessionId() 
                : "debug-" + UUID.randomUUID().toString();
        
        // Nếu không có error message, trả về thông báo yêu cầu chạy code trước
        if (debugQuestionDTO.getErrorMessage() == null || debugQuestionDTO.getErrorMessage().trim().isEmpty()) {
            return ResponseEntity.ok("Vui lòng chạy code trước để xem có lỗi gì không.");
        }
        
        // Nếu có lỗi, phân tích và gợi ý cách sửa
        String enhancedQuestion = String.format(
            "Phân tích và gợi ý cách sửa lỗi sau:\n" +
            "Code:\n```\n%s\n```\n" +
            "Lỗi:\n%s\n" +
            "Hãy trả lời theo format sau:\n" +
            "1. Nguyên nhân lỗi (ngắn gọn)\n" +
            "2. Cách sửa (chỉ đưa ra code cần sửa)\n" +
            "KHÔNG giải thích dài dòng. KHÔNG đưa ra code hoàn chỉnh.",
            debugQuestionDTO.getCode(),
            debugQuestionDTO.getErrorMessage()
        );
        
        // Gửi câu hỏi đến Gemini
        String response = geminiService.generateResponse(enhancedQuestion, currentSessionId);
        
        return ResponseEntity.ok(response);
    }
} 