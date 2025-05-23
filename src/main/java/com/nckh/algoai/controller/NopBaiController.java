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
import com.nckh.algoai.dto.ResponseObject;
import com.nckh.algoai.exception.ValidationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/nop-bai")
@Tag(name = "Nop bai", description = "API nộp bài")
public class NopBaiController {

    @Autowired
    private NopBaiService nopBaiService;

    @PostMapping("/quiz")
    @Operation(summary = "Nộp bài quiz", description = "Nộp bài quiz")
    public ResponseEntity<NopBaiEntity> nopBaiQuiz(
            @RequestBody NopBaiTapQuizDTO nopBaiTapQuizDTO) {
        NopBaiEntity nopBai = nopBaiService.luuBaiTapQuiz(nopBaiTapQuizDTO);
        return ResponseEntity.ok(nopBai);
    }
    
    @PostMapping("/code")
    @Operation(summary = "Nộp bài code", description = "Nộp bài code")
    public ResponseEntity<NopBaiEntity> nopBaiCode(
            @RequestBody NopBaiTapCodeDTO nopBaiTapCodeDTO) {
        NopBaiEntity nopBai = nopBaiService.luuBaiTapCode(nopBaiTapCodeDTO);
        return ResponseEntity.ok(nopBai);
    }
    
    @GetMapping("/danh-sach")
    @Operation(summary = "Lấy danh sách bài nộp", description = "Lấy danh sách bài nộp")
    public ResponseEntity<List<NopBaiEntity>> getDanhSachBaiNop(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam(value = "loaiBaiTap", required = false) String loaiBaiTap) {
        List<NopBaiEntity> danhSachBaiNop = nopBaiService.getDanhSachBaiNop(idNguoiDung, loaiBaiTap);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/dap-an-va-de-bai")
    @Operation(summary = "Lấy đáp án và đề bài đã lưu", description = "Lấy đáp án và đề bài đã lưu")
    public ResponseEntity<List<NopBaiEntity>> getDapAnVaDeBaiDaSave(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc) {
        List<NopBaiEntity> danhSachBaiNop = nopBaiService.getDapAnVaDeBaiDaSave(idNguoiDung, idBaiHoc);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/bai-tap-code")
    @Operation(summary = "Lấy bài tập code đã nộp", description = "Lấy bài tập code đã nộp")
    public ResponseEntity<List<NopBaiEntity>> getBaiTapCodeDaNop(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc) {
        List<NopBaiEntity> danhSachBaiNop = nopBaiService.getBaiTapCodeDaNop(idNguoiDung, idBaiHoc);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/bai-tap-quiz")
    @Operation(summary = "Lấy bài tập quiz đã nộp", description = "Lấy bài tập quiz đã nộp")
    public ResponseEntity<List<NopBaiEntity>> getBaiTapQuizDaNop(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc) {
        List<NopBaiEntity> danhSachBaiNop = nopBaiService.getBaiTapQuizDaNop(idNguoiDung, idBaiHoc);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/bai-tap-code-theo-muc-do")
    @Operation(summary = "Lấy bài tập code theo mức độ", description = "Lấy bài tập code theo mức độ")
    public ResponseEntity<List<NopBaiEntity>> getBaiTapCodeTheoMucDo(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc,
            @RequestParam("mucDo") String mucDo) {
        List<NopBaiEntity> danhSachBaiNop = nopBaiService.getBaiTapCodeTheoMucDo(idNguoiDung, idBaiHoc, mucDo);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/bai-tap-quiz-theo-muc-do")
    @Operation(summary = "Lấy bài tập quiz theo mức độ", description = "Lấy bài tập quiz theo mức độ")
    public ResponseEntity<List<NopBaiEntity>> getBaiTapQuizTheoMucDo(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc,
            @RequestParam("mucDo") String mucDo) {
        List<NopBaiEntity> danhSachBaiNop = nopBaiService.getBaiTapQuizTheoMucDo(idNguoiDung, idBaiHoc, mucDo);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/bai-tap-code-theo-muc-do-co-de-bai")
    @Operation(summary = "Lấy bài tập code theo mức độ và đề bài", description = "Lấy bài tập code theo mức độ và đề bài")
    public ResponseEntity<List<BaiTapCodeNopBaiDTO>> getBaiTapCodeTheoMucDoCoDeBai(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc,
            @RequestParam("mucDo") String mucDo) {
        List<BaiTapCodeNopBaiDTO> danhSachBaiNop = nopBaiService.getBaiTapCodeTheoMucDoCoDeBai(idNguoiDung, idBaiHoc, mucDo);
        return ResponseEntity.ok(danhSachBaiNop);
    }
    
    @GetMapping("/bai-tap-quiz-theo-muc-do-co-de-bai")
    @Operation(summary = "Lấy bài tập quiz theo mức độ và đề bài", description = "Lấy bài tập quiz theo mức độ và đề bài")
    public ResponseEntity<List<BaiTapQuizNopBaiDTO>> getBaiTapQuizTheoMucDoCoDeBai(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc,
            @RequestParam("mucDo") String mucDo) {
        List<BaiTapQuizNopBaiDTO> danhSachBaiNop = nopBaiService.getBaiTapQuizTheoMucDoCoDeBai(idNguoiDung, idBaiHoc, mucDo);
        return ResponseEntity.ok(danhSachBaiNop);
    }

    @PutMapping("/code")
    public ResponseEntity<ResponseObject> suaBaiNopCode(
            @RequestParam Integer idNguoiDung,
            @RequestParam Integer idBaiHoc,
            @RequestBody NopBaiTapCodeDTO nopBaiTapCodeDTO) {
        try {
            NopBaiEntity baiNop = nopBaiService.suaBaiNopCode(idNguoiDung, idBaiHoc, nopBaiTapCodeDTO);
            return ResponseEntity.ok(new ResponseObject("success", "Sửa bài nộp thành công", baiNop));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("error", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseObject("error", "Lỗi khi sửa bài nộp", null));
        }
    }

    @PutMapping("/quiz")
    public ResponseEntity<ResponseObject> suaBaiNopQuiz(
            @RequestParam Integer idNguoiDung,
            @RequestParam Integer idBaiHoc,
            @RequestBody NopBaiTapQuizDTO nopBaiTapQuizDTO) {
        try {
            NopBaiEntity baiNop = nopBaiService.suaBaiNopQuiz(idNguoiDung, idBaiHoc, nopBaiTapQuizDTO);
            return ResponseEntity.ok(new ResponseObject("success", "Sửa bài nộp thành công", baiNop));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("error", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseObject("error", "Lỗi khi sửa bài nộp", null));
        }
    }

    @GetMapping("/kiem-tra-da-lam-quiz")
    @Operation(summary = "Kiểm tra đã làm quiz", description = "Kiểm tra người dùng đã làm quiz chưa")
    public ResponseEntity<ResponseObject> kiemTraDaLamQuiz(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc) {
        try {
            boolean daLamQuiz = nopBaiService.kiemTraDaLamQuiz(idNguoiDung, idBaiHoc);
            return ResponseEntity.ok(new ResponseObject("success", "Kiểm tra thành công", daLamQuiz));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("error", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseObject("error", "Lỗi khi kiểm tra", null));
        }
    }

    @GetMapping("/kiem-tra-da-lam-code")
    @Operation(summary = "Kiểm tra đã làm code", description = "Kiểm tra người dùng đã làm code chưa")
    public ResponseEntity<ResponseObject> kiemTraDaLamCode(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc) {
        try {
            boolean daLamCode = nopBaiService.kiemTraDaLamCode(idNguoiDung, idBaiHoc);
            return ResponseEntity.ok(new ResponseObject("success", "Kiểm tra thành công", daLamCode));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("error", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseObject("error", "Lỗi khi kiểm tra", null));
        }
    }

    @GetMapping("/kiem-tra/quiz/theo-muc-do")
    public ResponseEntity<Boolean> kiemTraDaLamQuizTheoMucDo(
            @RequestParam Integer idNguoiDung,
            @RequestParam Integer idBaiHoc,
            @RequestParam String mucDo) {
        boolean daLam = nopBaiService.kiemTraDaLamQuizTheoMucDo(idNguoiDung, idBaiHoc, mucDo);
        return ResponseEntity.ok(daLam);
    }

    @GetMapping("/kiem-tra/code/theo-muc-do")
    public ResponseEntity<Boolean> kiemTraDaLamCodeTheoMucDo(
            @RequestParam Integer idNguoiDung,
            @RequestParam Integer idBaiHoc,
            @RequestParam String mucDo) {
        boolean daLam = nopBaiService.kiemTraDaLamCodeTheoMucDo(idNguoiDung, idBaiHoc, mucDo);
        return ResponseEntity.ok(daLam);
    }
} 