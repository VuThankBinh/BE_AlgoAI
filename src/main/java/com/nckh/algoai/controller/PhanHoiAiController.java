package com.nckh.algoai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nckh.algoai.entity.ResponseObject;
import com.nckh.algoai.entity.PhanHoiAiEntity;
import com.nckh.algoai.service.PhanHoiAiService;
import com.nckh.algoai.dto.PhanHoiAiDTO;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/phan-hoi-ai")
@Tag(name = "Phản hồi AI", description = "API phản hồi AI")
public class PhanHoiAiController {

    @Autowired
    private PhanHoiAiService phanHoiAiService;

    @PostMapping("/luu")
    public ResponseEntity<ResponseObject<PhanHoiAiEntity>> luuPhanHoiAi(@RequestBody PhanHoiAiDTO phanHoiAiDTO) {
        try {
            PhanHoiAiEntity phanHoiAi = phanHoiAiService.luuPhanHoiAi(phanHoiAiDTO);
            return ResponseEntity.ok(new ResponseObject<>(HttpStatus.OK, "Lưu phản hồi AI thành công", phanHoiAi));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    @GetMapping("/{idNopBai}")
    public ResponseEntity<ResponseObject<PhanHoiAiEntity>> getPhanHoiAi(@PathVariable Integer idNopBai) {
        try {
            PhanHoiAiEntity phanHoiAi = phanHoiAiService.getPhanHoiAi(idNopBai);
            return ResponseEntity.ok(new ResponseObject<>(HttpStatus.OK, "Lấy phản hồi AI thành công", phanHoiAi));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }
} 