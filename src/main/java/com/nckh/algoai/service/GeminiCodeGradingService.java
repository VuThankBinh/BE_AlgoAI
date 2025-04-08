// src/main/java/com/nckh/algoai/service/GeminiCodeGradingService.java
package com.nckh.algoai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import com.nckh.algoai.dto.CodeGradingRequestDTO;
import com.nckh.algoai.dto.CodeGradingResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class GeminiCodeGradingService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CodeGradingResponseDTO gradeCode(CodeGradingRequestDTO request) {
        try {
            // Tạo prompt cho Gemini
            String prompt = createGradingPrompt(request);
            
            // Gọi API Gemini
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", new Object[]{
                Map.of(
                    "parts", new Object[]{
                        Map.of("text", prompt)
                    }
                )
            });
            requestBody.put("generationConfig", Map.of(
                "temperature", 0.7,
                "maxOutputTokens", 1000
            ));

            // Thêm headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", apiKey);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            // Gọi API
            ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                String.class
            );

            // Xử lý kết quả
            String geminiResponse = response.getBody();
            return parseGeminiResponse(geminiResponse);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi gọi API Gemini: " + e.getMessage());
        }
    }

    private String createGradingPrompt(CodeGradingRequestDTO request) {
        return String.format("""
            Bạn là một giáo viên lập trình chuyên nghiệp. Hãy chấm điểm và phân tích code sau đây:
            
            Đề bài: %s
            
            Code của học viên:
            %s
            
            Hãy phân tích và chấm điểm theo các tiêu chí:
            1. Độ chính xác của thuật toán (0-40 điểm)
            2. Cấu trúc code và phong cách lập trình (0-30 điểm)
            3. Xử lý các trường hợp đặc biệt (0-20 điểm)
            4. Hiệu suất và tối ưu hóa (0-10 điểm)
            
            Trả về kết quả dưới dạng JSON với format:
            {
                "totalScore": số điểm tổng,
                "accuracyScore": điểm độ chính xác,
                "structureScore": điểm cấu trúc code,
                "edgeCasesScore": điểm xử lý trường hợp đặc biệt,
                "performanceScore": điểm hiệu suất,
                "feedback": "Nhận xét chi tiết",
                "suggestions": ["Gợi ý cải thiện 1", "Gợi ý cải thiện 2"]
            }
            """, request.getExerciseDescription(), request.getCode());
    }

    private CodeGradingResponseDTO parseGeminiResponse(String geminiResponse) {
        try {
            // Parse JSON response từ Gemini
            Map<String, Object> responseMap = objectMapper.readValue(geminiResponse, Map.class);
            
            // Lấy nội dung từ response
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseMap.get("candidates");
            Map<String, Object> firstCandidate = candidates.get(0);
            Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            Map<String, Object> firstPart = parts.get(0);
            String text = (String) firstPart.get("text");
            
            // Xử lý markdown format
            if (text.startsWith("```json")) {
                text = text.substring(7); // Bỏ ```json
                text = text.substring(0, text.lastIndexOf("```")); // Bỏ ``` ở cuối
            }
            
            // Parse nội dung thành DTO
            return objectMapper.readValue(text.trim(), CodeGradingResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi parse response từ Gemini: " + e.getMessage());
        }
    }
}