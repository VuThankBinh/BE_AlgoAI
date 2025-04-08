package com.nckh.algoai.service;

import com.nckh.algoai.model.ChatMessage;
import com.nckh.algoai.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChatbotService {

    @Autowired
    private GeminiService geminiService;
    
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage processMessage(String userMessage, String sessionId) {
        ChatMessage response = new ChatMessage();
        response.setId(UUID.randomUUID().toString());
        response.setTimestamp(LocalDateTime.now());
        response.setType(ChatMessage.MessageType.CHAT);
        response.setSender("Bot");
        response.setRole("assistant");
        response.setSessionId(sessionId);

        // Xử lý tin nhắn và tạo câu trả lời
        String botResponse = generateBotResponse(userMessage.toLowerCase(), sessionId);
        response.setContent(botResponse);

        return response;
    }

    private String generateBotResponse(String userMessage, String sessionId) {
        // Thử sử dụng Gemini trước
        String geminiResponse = geminiService.generateResponse(userMessage, sessionId);
        if (geminiResponse != null && !geminiResponse.contains("Cấu hình Gemini API chưa được thiết lập")) {
            return geminiResponse;
        }
        
        // Nếu Gemini không khả dụng, sử dụng logic cơ bản
        return generateBasicResponse(userMessage);
    }
    
    private String generateBasicResponse(String userMessage) {
        // Logic xử lý tin nhắn và tạo câu trả lời cơ bản
        if (userMessage.contains("xin chào") || userMessage.contains("hello") || userMessage.contains("hi")) {
            return "Xin chào! Tôi là AlgoAI Bot, tôi có thể giúp gì cho bạn?";
        } else if (userMessage.contains("giúp") || userMessage.contains("help")) {
            return "Tôi có thể giúp bạn:\n" +
                   "1. Giải đáp thắc mắc về lập trình\n" +
                   "2. Hướng dẫn sử dụng code editor\n" +
                   "3. Giải thích các khái niệm về thuật toán\n" +
                   "Bạn cần giúp đỡ về vấn đề gì?";
        } else if (userMessage.contains("cảm ơn") || userMessage.contains("thanks")) {
            return "Không có gì! Nếu bạn cần thêm hỗ trợ, đừng ngần ngại hỏi nhé!";
        } else if (userMessage.contains("python") || userMessage.contains("java") || userMessage.contains("javascript")) {
            return "Tôi có thể giúp bạn với các vấn đề về " + 
                   (userMessage.contains("python") ? "Python" : 
                    userMessage.contains("java") ? "Java" : "JavaScript") + 
                   ". Bạn cần hỗ trợ gì cụ thể?";
        } else if (userMessage.contains("thuật toán") || userMessage.contains("algorithm")) {
            return "Tôi có thể giúp bạn tìm hiểu về các thuật toán. Bạn muốn tìm hiểu về thuật toán nào?";
        } else {
            return "Xin lỗi, tôi chưa hiểu rõ ý bạn. Bạn có thể nói rõ hơn không?";
        }
    }
    
    /**
     * Lấy lịch sử chat của một phiên
     * @param sessionId ID phiên chat
     * @return Danh sách tin nhắn trong phiên chat
     */
    public List<ChatMessage> getChatHistory(String sessionId) {
        return chatMessageRepository.getMessagesBySessionId(sessionId);
    }
} 