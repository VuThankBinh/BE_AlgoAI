package com.nckh.algoai.controller;

import com.nckh.algoai.model.ChatMessage;
import com.nckh.algoai.service.ChatbotService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Lấy sessionId từ header
        String sessionId = (String) headerAccessor.getSessionAttributes().get("sessionId");
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString();
            headerAccessor.getSessionAttributes().put("sessionId", sessionId);
        }
        
        // Xử lý tin nhắn từ người dùng
        ChatMessage botResponse = chatbotService.processMessage(chatMessage.getContent(), sessionId);
        return botResponse;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, 
                             SimpMessageHeaderAccessor headerAccessor) {
        // Thêm người dùng vào WebSocket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        
        // Tạo sessionId mới
        String sessionId = UUID.randomUUID().toString();
        headerAccessor.getSessionAttributes().put("sessionId", sessionId);
        
        // Tạo tin nhắn chào mừng
        ChatMessage welcomeMessage = new ChatMessage();
        welcomeMessage.setId(UUID.randomUUID().toString());
        welcomeMessage.setType(ChatMessage.MessageType.JOIN);
        welcomeMessage.setContent("Xin chào " + chatMessage.getSender() + "! Tôi là AlgoAI Bot, tôi có thể giúp gì cho bạn?");
        welcomeMessage.setSender("Bot");
        
        return welcomeMessage;
    }
} 