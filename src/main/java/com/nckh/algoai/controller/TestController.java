package com.nckh.algoai.controller;

import com.nckh.algoai.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private GeminiService geminiService;

    @GetMapping("/gemini")
    public String testGemini() {
        String testMessage = "Xin chào, bạn có thể giới thiệu về bản thân không?";
        String sessionId = "test-session-" + UUID.randomUUID().toString();
        return geminiService.generateResponse(testMessage, sessionId);
    }
} 