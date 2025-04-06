package com.nckh.algoai.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nckh.algoai.dto.BaiTapQuizDTO;
import com.nckh.algoai.service.BaiTapQuizService;

@RestController
@RequestMapping("/api/bai-tap-quiz")
public class BaiTapQuizController {

    @Autowired
    private BaiTapQuizService baiTapQuizService;

    @GetMapping("/theo-muc-do")
    public ResponseEntity<List<BaiTapQuizDTO>> getBaiTapQuizTheoMucDo(
            @RequestParam("mucDo") String mucDo,
            @RequestParam("idBaiHoc") Integer idBaiHoc) {
        List<BaiTapQuizDTO> baiTapQuizList = baiTapQuizService.getBaiTapQuizTheoMucDo(mucDo, idBaiHoc);
        return ResponseEntity.ok(baiTapQuizList);
    }
} 