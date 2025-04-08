package com.nckh.algoai.service;

import com.nckh.algoai.model.ChatMessage;
import com.nckh.algoai.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key:}")
    private String apiKey;

    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public String generateResponse(String userMessage, String sessionId) {
        try {
            if (apiKey == null || apiKey.isEmpty()) {
                return "Cấu hình Gemini API chưa được thiết lập. Vui lòng kiểm tra lại cấu hình.";
            }

            // Lưu tin nhắn của người dùng
            ChatMessage userChatMessage = new ChatMessage("user", userMessage, sessionId);
            chatMessageRepository.saveMessage(userChatMessage);
            
            // Lấy lịch sử chat gần đây (tối đa 10 tin nhắn)
            List<ChatMessage> chatHistory = chatMessageRepository.getRecentMessages(sessionId, 10);
            
            // Tạo nội dung yêu cầu với lịch sử chat
            List<Map<String, Object>> contents = new ArrayList<>();
            
            // Thêm lịch sử chat vào yêu cầu
            for (ChatMessage message : chatHistory) {
                Map<String, Object> content = new HashMap<>();
                Map<String, Object> textPart = new HashMap<>();
                textPart.put("text", message.getContent());
                content.put("parts", new Object[]{textPart});
                content.put("role", message.getRole().equals("assistant") ? "model" : "user");
                contents.add(content);
            }
            
            // Thêm tin nhắn hiện tại
            Map<String, Object> currentContent = new HashMap<>();
            Map<String, Object> currentTextPart = new HashMap<>();
            currentTextPart.put("text", userMessage);
            currentContent.put("parts", new Object[]{currentTextPart});
            currentContent.put("role", "user");
            contents.add(currentContent);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = new HashMap<>();
            body.put("contents", contents);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            
            String url = apiUrl + "?key=" + apiKey;
            Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
            
            String assistantResponse = "Xin lỗi, tôi không thể xử lý yêu cầu của bạn lúc này.";
            
            if (response != null && response.containsKey("candidates")) {
                List<Object> candidates = (List<Object>) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> candidate = (Map<String, Object>) candidates.get(0);
                    Map<String, Object> content = (Map<String, Object>) candidate.get("content");
                    List<Object> responseParts = (List<Object>) content.get("parts");
                    if (!responseParts.isEmpty()) {
                        Map<String, Object> part = (Map<String, Object>) responseParts.get(0);
                        assistantResponse = (String) part.get("text");
                    }
                }
            }
            
            // Lưu phản hồi của trợ lý
            ChatMessage assistantChatMessage = new ChatMessage("assistant", assistantResponse, sessionId);
            chatMessageRepository.saveMessage(assistantChatMessage);
            
            return assistantResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return "Đã xảy ra lỗi khi xử lý yêu cầu của bạn.";
        }
    }
    
    // Phương thức để xóa lịch sử chat của một phiên
    public void clearChatHistory(String sessionId) {
        chatMessageRepository.clearSession(sessionId);
    }
} 