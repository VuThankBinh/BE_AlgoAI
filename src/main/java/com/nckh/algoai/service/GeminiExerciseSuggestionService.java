package com.nckh.algoai.service;

import com.nckh.algoai.dto.ExerciseSuggestionRequestDTO;
import com.nckh.algoai.dto.ExerciseSuggestionResponseDTO;
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
public class GeminiExerciseSuggestionService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ExerciseSuggestionResponseDTO suggestExercises(ExerciseSuggestionRequestDTO request) {
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
                "maxOutputTokens", 2000
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
            ExerciseSuggestionResponseDTO errorResponse = new ExerciseSuggestionResponseDTO();
            errorResponse.setErrorMessage("Lỗi khi gợi ý bài tập: " + e.getMessage());
            return errorResponse;
        }
    }

    private String createSuggestionPrompt(ExerciseSuggestionRequestDTO request) {
        return String.format("""
            Bạn là một giáo viên lập trình chuyên nghiệp. Hãy tạo các bài tập lập trình và câu hỏi trắc nghiệm dựa trên thông tin sau:
            
            Chủ đề: %s
            Mức độ khó: %s
            Ngôn ngữ lập trình: %s
            Loại bài tập: %s
            Thông tin lý thuyết: %s
            
            Hãy tạo:
            1. Các bài tập lập trình (nếu exerciseType là 'code' hoặc 'both')
            2. Các câu hỏi trắc nghiệm (nếu exerciseType là 'quiz' hoặc 'both')
            
            Trả về kết quả dưới dạng JSON với format:
            {
                "codingExercises": [
                    {
                        "title": "Tên bài tập",
                        "description": "Mô tả bài tập",
                        "difficultyLevel": "Mức độ khó",
                        "programmingLanguage": "Ngôn ngữ lập trình",
                        "expectedOutput": "Đầu ra mong đợi",
                        "testCases": ["Test case 1", "Test case 2", ...],
                        "constraints": ["Ràng buộc 1", "Ràng buộc 2", ...],
                        "solution": "Lời giải mẫu"
                    }
                ],
                "quizQuestions": [
                    {
                        "question": "Câu hỏi trắc nghiệm",
                        "options": ["Đáp án A", "Đáp án B", "Đáp án C", "Đáp án D"],
                        "correctAnswer": "Đáp án đúng",
                        "explanation": "Giải thích đáp án"
                    }
                ]
            }
            """, 
            request.getTopic(),
            request.getDifficultyLevel(),
            request.getProgrammingLanguage(),
            request.getExerciseType(),
            request.getTheoryInfo());
    }

    private ExerciseSuggestionResponseDTO parseGeminiResponse(String geminiResponse) {
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
            return objectMapper.readValue(text.trim(), ExerciseSuggestionResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi parse response từ Gemini: " + e.getMessage());
        }
    }
} 