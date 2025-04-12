package com.nckh.algoai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nckh.algoai.service.PhanHoiAiService;
import com.nckh.algoai.dto.PhanHoiAiDTO;
import com.nckh.algoai.entity.PhanHoiAiEntity;
import com.nckh.algoai.dto.ResponseObject;
import com.nckh.algoai.exception.ValidationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/phan-hoi-ai")
@Tag(name = "Phản hồi AI", description = "API phản hồi AI")
public class PhanHoiAiController {

    @Autowired
    private PhanHoiAiService phanHoiAiService;

    @PostMapping
    @Operation(summary = "Lưu phản hồi AI", description = "Lưu phản hồi AI cho bài nộp")
    public ResponseEntity<ResponseObject> luuPhanHoiAi(@RequestBody PhanHoiAiDTO phanHoiAiDTO) {
        try {
            PhanHoiAiEntity phanHoiAi = phanHoiAiService.luuPhanHoiAi(phanHoiAiDTO);
            return ResponseEntity.ok(new ResponseObject("success", "Lưu phản hồi AI thành công", phanHoiAi));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("error", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseObject("error", "Lỗi khi lưu phản hồi AI", null));
        }
    }

    @GetMapping("/{idNopBai}")
    @Operation(summary = "Lấy phản hồi AI", description = "Lấy phản hồi AI theo ID bài nộp")
    public ResponseEntity<ResponseObject> getPhanHoiAi(@PathVariable Integer idNopBai) {
        try {
            PhanHoiAiEntity phanHoiAi = phanHoiAiService.getPhanHoiAi(idNopBai);
            return ResponseEntity.ok(new ResponseObject("success", "Lấy phản hồi AI thành công", phanHoiAi));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("error", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseObject("error", "Lỗi khi lấy phản hồi AI", null));
        }
    }

    @PutMapping("/{idNopBai}")
    @Operation(summary = "Sửa phản hồi AI", description = "Sửa phản hồi AI theo ID bài nộp")
    public ResponseEntity<ResponseObject> suaPhanHoiAi(
            @PathVariable Integer idNopBai,
            @RequestBody PhanHoiAiDTO phanHoiAiDTO) {
        try {
            PhanHoiAiEntity phanHoiAi = phanHoiAiService.suaPhanHoiAi(idNopBai, phanHoiAiDTO);
            return ResponseEntity.ok(new ResponseObject("success", "Sửa phản hồi AI thành công", phanHoiAi));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("error", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseObject("error", "Lỗi khi sửa phản hồi AI", null));
        }
    }
} 