package com.nckh.algoai.controller;

import com.nckh.algoai.dto.CodeExecutionRequest;
import com.nckh.algoai.entity.ResponseObject;
import com.nckh.algoai.service.CodeExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/code")
@Tag(name = "Code Execution Controller", description = "API thực thi code thông qua JDoodle")
public class CodeExecutionController {

    @Autowired
    private CodeExecutionService codeExecutionService;

    @Operation(summary = "Thực thi code", description = "Thực thi code thông qua JDoodle API")
    @PostMapping("/execute")
    public ResponseEntity<ResponseObject<Map<String, Object>>> executeCode(
            @Parameter(description = "Request thực thi code", required = true)
            @RequestBody CodeExecutionRequest request) {
        try {
            Map<String, Object> result = codeExecutionService.executeCode(request);
            
            if (result.containsKey("error")) {
                return ResponseEntity.badRequest()
                    .body(new ResponseObject<>(
                        org.springframework.http.HttpStatus.BAD_REQUEST,
                        (String) result.get("error"),
                        result
                    ));
            }
            
            return ResponseEntity.ok(
                new ResponseObject<>(
                    org.springframework.http.HttpStatus.OK,
                    "Thực thi code thành công",
                    result
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject<>(
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lỗi khi thực thi code: " + e.getMessage(),
                    null
                ));
        }
    }
} 