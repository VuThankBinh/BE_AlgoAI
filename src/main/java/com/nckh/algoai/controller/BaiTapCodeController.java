package com.nckh.algoai.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nckh.algoai.dto.BaiTapCodeDTO;
import com.nckh.algoai.service.BaiTapCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/bai-tap-code")
@Tag(name = "Bài tập code", description = "API get bài tập code theo mức độ")
public class BaiTapCodeController {

    @Autowired
    private BaiTapCodeService baiTapCodeService;

    @GetMapping("/theo-muc-do")
    public ResponseEntity<List<BaiTapCodeDTO>> getBaiTapCodeTheoMucDo(
            @RequestParam("mucDo") String mucDo,
            @RequestParam("idBaiHoc") Integer idBaiHoc) {
        List<BaiTapCodeDTO> baiTapCodeList = baiTapCodeService.getBaiTapCodeTheoMucDo(mucDo, idBaiHoc);
        return ResponseEntity.ok(baiTapCodeList);
    }
} 