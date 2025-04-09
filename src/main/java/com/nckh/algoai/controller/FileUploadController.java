package com.nckh.algoai.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.nckh.algoai.entity.ResponseObject;
import com.nckh.algoai.exception.BadRequestException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/upload")
@Tag(name = "Upload file", description = "API upload file")
public class FileUploadController {
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/images/";

    @Operation(summary = "Upload file")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
        @Parameter(description = "File to upload", required = true)
        @RequestParam("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File không được để trống");
        }

        // Kiểm tra kích thước file
        if (file.getSize() > 20 * 1024 * 1024) { // 20MB
            return ResponseEntity.badRequest().body("Kích thước file không được vượt quá 20MB");
        }

        try {
            // Tạo thư mục nếu chưa tồn tại
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Tạo đường dẫn lưu file
            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            
            // Lưu file
            file.transferTo(filePath.toFile());
            return ResponseEntity.ok("File đã được lưu tại: " + filePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Lỗi khi lưu file: " + e.getMessage());
        }
    }
}


