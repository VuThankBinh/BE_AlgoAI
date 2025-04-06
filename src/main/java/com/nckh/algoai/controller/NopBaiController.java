package com.nckh.algoai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nckh.algoai.dto.NopBaiTapCodeDTO;
import com.nckh.algoai.dto.NopBaiTapQuizDTO;
import com.nckh.algoai.dto.BaiTapCodeNopBaiDTO;
import com.nckh.algoai.dto.BaiTapQuizNopBaiDTO;
import com.nckh.algoai.entity.NopBaiEntity;
import com.nckh.algoai.service.NopBaiService;
import java.util.List;

@RestController
@RequestMapping("/api/nop-bai")
public class NopBaiController {

    @Autowired
    private NopBaiService nopBaiService;

    @PostMapping("/quiz")
    public ResponseEntity<NopBaiEntity> nopBaiQuiz(
            @RequestBody NopBaiTapQuizDTO nopBaiTapQuizDTO) {
        NopBaiEntity nopBai = nopBaiService.luuBaiTapQuiz(nopBaiTapQuizDTO);
        return ResponseEntity.ok(nopBai);
    }
    
    @PostMapping("/code")
    public ResponseEntity<NopBaiEntity> nopBaiCode(
            @RequestBody NopBaiTapCodeDTO nopBaiTapCodeDTO) {
        NopBaiEntity nopBai = nopBaiService.luuBaiTapCode(nopBaiTapCodeDTO);
        return ResponseEntity.ok(nopBai);
    }
    
    @GetMapping("/danh-sach")
    public ResponseEntity<List<NopBaiEntity>> getDanhSachBaiNop(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam(value = "loaiBaiTap", required = false) String loaiBaiTap) {
        List<NopBaiEntity> danhSachBaiNop = nopBaiService.getDanhSachBaiNop(idNguoiDung, loaiBaiTap);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/dap-an-va-de-bai")
    public ResponseEntity<List<NopBaiEntity>> getDapAnVaDeBaiDaSave(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc) {
        List<NopBaiEntity> danhSachBaiNop = nopBaiService.getDapAnVaDeBaiDaSave(idNguoiDung, idBaiHoc);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/bai-tap-code")
    public ResponseEntity<List<NopBaiEntity>> getBaiTapCodeDaNop(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc) {
        List<NopBaiEntity> danhSachBaiNop = nopBaiService.getBaiTapCodeDaNop(idNguoiDung, idBaiHoc);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/bai-tap-quiz")
    public ResponseEntity<List<NopBaiEntity>> getBaiTapQuizDaNop(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc) {
        List<NopBaiEntity> danhSachBaiNop = nopBaiService.getBaiTapQuizDaNop(idNguoiDung, idBaiHoc);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/bai-tap-code-theo-muc-do")
    public ResponseEntity<List<NopBaiEntity>> getBaiTapCodeTheoMucDo(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc,
            @RequestParam("mucDo") String mucDo) {
        List<NopBaiEntity> danhSachBaiNop = nopBaiService.getBaiTapCodeTheoMucDo(idNguoiDung, idBaiHoc, mucDo);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/bai-tap-quiz-theo-muc-do")
    public ResponseEntity<List<NopBaiEntity>> getBaiTapQuizTheoMucDo(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc,
            @RequestParam("mucDo") String mucDo) {
        List<NopBaiEntity> danhSachBaiNop = nopBaiService.getBaiTapQuizTheoMucDo(idNguoiDung, idBaiHoc, mucDo);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/bai-tap-code-theo-muc-do-co-de-bai")
    public ResponseEntity<List<BaiTapCodeNopBaiDTO>> getBaiTapCodeTheoMucDoCoDeBai(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc,
            @RequestParam("mucDo") String mucDo) {
        List<BaiTapCodeNopBaiDTO> danhSachBaiNop = nopBaiService.getBaiTapCodeTheoMucDoCoDeBai(idNguoiDung, idBaiHoc, mucDo);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/bai-tap-quiz-theo-muc-do-co-de-bai")
    public ResponseEntity<List<BaiTapQuizNopBaiDTO>> getBaiTapQuizTheoMucDoCoDeBai(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc,
            @RequestParam("mucDo") String mucDo) {
        List<BaiTapQuizNopBaiDTO> danhSachBaiNop = nopBaiService.getBaiTapQuizTheoMucDoCoDeBai(idNguoiDung, idBaiHoc, mucDo);
        return ResponseEntity.ok(danhSachBaiNop);
    }
} 