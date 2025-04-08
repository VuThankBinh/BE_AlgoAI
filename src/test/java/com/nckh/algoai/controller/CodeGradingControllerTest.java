package com.nckh.algoai.controller;

import com.nckh.algoai.dto.CodeGradingRequestDTO;
import com.nckh.algoai.dto.CodeGradingResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CodeGradingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGradeCode() {
        // Tạo request test
        CodeGradingRequestDTO request = new CodeGradingRequestDTO();
        request.setCode("public int sumEvenNumbers(int[] arr) { int sum = 0; for (int num : arr) { if (num % 2 == 0) { sum += num; } } return sum; }");
        
        request.setExerciseDescription("Viết hàm tính tổng các số chẵn trong mảng. Input: Mảng số nguyên Output: Tổng các số chẵn");
            
        request.setProgrammingLanguage("java");
        request.setDifficultyLevel("Easy");
        request.setExpectedOutput("Input: [1, 2, 3, 4, 5] Output: 6 Input: [2, 4, 6, 8] Output: 20 Input: [1, 3, 5, 7] Output: 0");
            
        request.setTestCases("Test Case 1: Input: [1, 2, 3, 4, 5] Expected Output: 6 Test Case 2: Input: [2, 4, 6, 8] Expected Output: 20 Test Case 3: Input: [1, 3, 5, 7] Expected Output: 0 Test Case 4: Input: [] Expected Output: 0 Test Case 5: Input: [0, 0, 0] Expected Output: 0");
            
        request.setConstraints("1. Không được sử dụng thư viện ngoài 2. Độ phức tạp thời gian phải là O(n) 3. Độ phức tạp không gian phải là O(1) 4. Xử lý được mảng rỗng");

        // Gọi API
        ResponseEntity<CodeGradingResponseDTO> response = restTemplate.postForEntity(
            "/api/code-grading/grade",
            request,
            CodeGradingResponseDTO.class
        );

        // Kiểm tra kết quả
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        CodeGradingResponseDTO responseBody = response.getBody();
        assertTrue(responseBody.getTotalScore() >= 0 && responseBody.getTotalScore() <= 100);
        assertNotNull(responseBody.getFeedback());
        assertNotNull(responseBody.getSuggestions());
        
        // In kết quả chi tiết
        System.out.println("\n=== Kết quả chấm điểm ===");
        System.out.println("Tổng điểm: " + responseBody.getTotalScore());
        System.out.println("\nNhận xét:");
        System.out.println(responseBody.getFeedback());
        System.out.println("\nGợi ý cải thiện:");
        responseBody.getSuggestions().forEach(System.out::println);
    }

    @Test
    public void testFindSecondLargest() {
        // Tạo request test
        CodeGradingRequestDTO request = new CodeGradingRequestDTO();
        request.setCode("""
            public int findSecondLargest(int[] arr) {
                if (arr == null || arr.length < 2) {
                    return -1;
                }
                
                int first = Integer.MIN_VALUE;
                int second = Integer.MIN_VALUE;
                
                for (int num : arr) {
                    if (num > first) {
                        second = first;
                        first = num;
                    } else if (num > second && num != first) {
                        second = num;
                    }
                }
                
                return second == Integer.MIN_VALUE ? -1 : second;
            }
            """);
        
        request.setExerciseDescription("""
            Viết hàm tìm số lớn thứ hai trong mảng số nguyên.
            Input: Mảng số nguyên
            Output: Số lớn thứ hai trong mảng, trả về -1 nếu không tìm thấy
            """);
            
        request.setProgrammingLanguage("java");
        request.setDifficultyLevel("Medium");
        request.setExpectedOutput("""
            Input: [1, 2, 3, 4, 5]
            Output: 4
            
            Input: [5, 5, 4, 3, 2]
            Output: 4
            
            Input: [1, 1, 1, 1]
            Output: -1
            
            Input: [1]
            Output: -1
            """);
            
        request.setTestCases("""
            Test Case 1: Mảng bình thường
            Input: [1, 2, 3, 4, 5]
            Expected Output: 4
            
            Test Case 2: Mảng có số trùng lặp
            Input: [5, 5, 4, 3, 2]
            Expected Output: 4
            
            Test Case 3: Mảng toàn số giống nhau
            Input: [1, 1, 1, 1]
            Expected Output: -1
            
            Test Case 4: Mảng một phần tử
            Input: [1]
            Expected Output: -1
            
            Test Case 5: Mảng rỗng
            Input: []
            Expected Output: -1
            
            Test Case 6: Mảng có số âm
            Input: [-1, -2, -3, -4, -5]
            Expected Output: -2
            
            Test Case 7: Mảng có số 0
            Input: [0, 0, 0, 1]
            Expected Output: 0
            
            Test Case 8: Mảng có số lớn nhất ở cuối
            Input: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
            Expected Output: 9
            """);
            
        request.setConstraints("""
            1. Không được sử dụng thư viện ngoài
            2. Độ phức tạp thời gian phải là O(n)
            3. Độ phức tạp không gian phải là O(1)
            4. Xử lý được các trường hợp đặc biệt:
               - Mảng null
               - Mảng rỗng
               - Mảng một phần tử
               - Mảng toàn số giống nhau
               - Mảng có số âm
               - Mảng có số 0
            """);

        // Gọi API
        ResponseEntity<CodeGradingResponseDTO> response = restTemplate.postForEntity(
            "/api/code-grading/grade",
            request,
            CodeGradingResponseDTO.class
        );

        // Kiểm tra kết quả
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        CodeGradingResponseDTO responseBody = response.getBody();
        assertTrue(responseBody.getTotalScore() >= 0 && responseBody.getTotalScore() <= 100);
        assertNotNull(responseBody.getFeedback());
        assertNotNull(responseBody.getSuggestions());
        
        // In kết quả chi tiết
        System.out.println("\n=== Kết quả chấm điểm ===");
        System.out.println("Tổng điểm: " + responseBody.getTotalScore());
        System.out.println("\nNhận xét:");
        System.out.println(responseBody.getFeedback());
        System.out.println("\nGợi ý cải thiện:");
        responseBody.getSuggestions().forEach(System.out::println);
    }
} 