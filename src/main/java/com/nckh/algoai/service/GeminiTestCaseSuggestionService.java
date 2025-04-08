package com.nckh.algoai.service;

import com.nckh.algoai.dto.TestCaseSuggestionRequestDTO;
import com.nckh.algoai.dto.TestCaseSuggestionResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class GeminiTestCaseSuggestionService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TestCaseSuggestionResponseDTO suggestTestCases(TestCaseSuggestionRequestDTO request) {
        try {
            // Tạo prompt cho Gemini
            String prompt = createSuggestionPrompt(request);
            
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
            TestCaseSuggestionResponseDTO errorResponse = new TestCaseSuggestionResponseDTO();
            errorResponse.setErrorMessage("Lỗi khi gợi ý test cases: " + e.getMessage());
            return errorResponse;
        }
    }

    private String createSuggestionPrompt(TestCaseSuggestionRequestDTO request) {
        return String.format("""
            Bạn là một chuyên gia về kiểm thử phần mềm. Hãy gợi ý các test cases cho bài tập lập trình sau:
            
            Đề bài: %s
            Ngôn ngữ lập trình: %s
            Mức độ khó: %s
            Ràng buộc: %s
            
            Hãy gợi ý:
            1. Các test cases thông thường
            2. Các edge cases (trường hợp đặc biệt)
            3. Đầu ra mong đợi cho mỗi test case
            
            Trả về kết quả dưới dạng JSON với format:
            {
                "testCases": [
                    "Test Case 1: Mô tả test case 1",
                    "Test Case 2: Mô tả test case 2",
                    ...
                ],
                "edgeCases": [
                    "Edge Case 1: Mô tả edge case 1",
                    "Edge Case 2: Mô tả edge case 2",
                    ...
                ],
                "expectedOutput": "Mô tả đầu ra mong đợi cho các test cases"
            }
            """, 
            request.getExerciseDescription(),
            request.getProgrammingLanguage(),
            request.getDifficultyLevel(),
            request.getConstraints());
    }

    private TestCaseSuggestionResponseDTO parseGeminiResponse(String geminiResponse) {
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
            return objectMapper.readValue(text.trim(), TestCaseSuggestionResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi parse response từ Gemini: " + e.getMessage());
        }
    }
} 