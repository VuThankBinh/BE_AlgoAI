package com.nckh.algoai.service;

import com.nckh.algoai.dto.CodeExecutionRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CodeExecutionService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String JDOODLE_API_URL = "https://api.jdoodle.com/v1/execute";

    @SuppressWarnings("unchecked")
    public Map<String, Object> executeCode(CodeExecutionRequest request) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (request.getScript() == null || request.getScript().isEmpty()) {
                throw new IllegalArgumentException("Script không được để trống");
            }

            // Tạo headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Tạo request body
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("script", request.getScript());
            requestBody.put("language", "sql");
            requestBody.put("versionIndex", "3");
            requestBody.put("stdin","");
            requestBody.put("clientId", "37b74c2b9f31a362ad8ecb4ecbb22441");
            requestBody.put("clientSecret", "c01683242339952959b606fa035fbaeb59b6894fb11bf3dfa8415994c0b5ba0c");

            // Tạo HTTP entity
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            // Gửi request đến JDoodle API
            Map<String, Object> response = restTemplate.postForObject(
                JDOODLE_API_URL,
                entity,
                Map.class
            );

            // Xử lý response
            if (response != null) {
                if (response.containsKey("error") && "Daily limit reached".equals(response.get("error")) 
                    && Integer.valueOf(429).equals(response.get("statusCode"))) {
                    throw new RuntimeException("Đã đạt giới hạn số lần thực thi hàng ngày. Vui lòng thử lại sau.");
                }
                return response;
            }

            throw new RuntimeException("Không nhận được phản hồi từ JDoodle API");

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("statusCode", 500);
            return errorResponse;
        }
    }
} 