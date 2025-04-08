package com.nckh.algoai.repository;

import com.nckh.algoai.model.ChatMessage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ChatMessageRepository {
    
    // Lưu trữ tin nhắn theo sessionId
    private final Map<String, List<ChatMessage>> messageStore = new ConcurrentHashMap<>();
    
    // Lưu trữ tin nhắn theo thời gian
    private final List<ChatMessage> allMessages = new ArrayList<>();
    
    public void saveMessage(ChatMessage message) {
        // Lưu tin nhắn vào danh sách tất cả tin nhắn
        allMessages.add(message);
        
        // Lưu tin nhắn theo sessionId
        messageStore.computeIfAbsent(message.getSessionId(), k -> new ArrayList<>())
                   .add(message);
    }
    
    public List<ChatMessage> getMessagesBySessionId(String sessionId) {
        return messageStore.getOrDefault(sessionId, new ArrayList<>());
    }
    
    public List<ChatMessage> getRecentMessages(String sessionId, int limit) {
        List<ChatMessage> sessionMessages = getMessagesBySessionId(sessionId);
        return sessionMessages.stream()
                .sorted((m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    public List<ChatMessage> getAllMessages() {
        return new ArrayList<>(allMessages);
    }
    
    public void clearSession(String sessionId) {
        messageStore.remove(sessionId);
    }
} 