package com.nckh.algoai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nckh.algoai.entity.ResponseObject;
import com.nckh.algoai.entity.TienDoEntity;
import com.nckh.algoai.service.TienDoService;
import com.nckh.algoai.dto.BaiHocTienDoDTO;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tien-do")
@Tag(name = "Tien do", description = "API tạo tình trạng học tập")
public class TienDoController {

    @Autowired
    private TienDoService tienDoService;

    @PostMapping("/cap-nhat")
    @Operation(summary = "Cập nhật tình trạng học tập", description = "Cập nhật tình trạng học tập")
    public ResponseEntity<TienDoEntity> capNhatTienDo(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc) {
        TienDoEntity tienDo = tienDoService.capNhatTienDo(idNguoiDung, idBaiHoc);
        return ResponseEntity.ok(tienDo);
    }
    
    @GetMapping("/bai-hoc")
    @Operation(summary = "Lấy danh sách bài học theo trạng thái", description = "Lấy danh sách bài học theo trạng thái")
    public ResponseEntity<List<BaiHocTienDoDTO>> getDanhSachBaiHocTheoTrangThai(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idKhoaHoc") Integer idKhoaHoc,
            @RequestParam(value = "trangThai", required = false) String trangThai) {
        List<BaiHocTienDoDTO> danhSachBaiHoc = tienDoService.getDanhSachBaiHocTheoTrangThai(idNguoiDung, idKhoaHoc, trangThai);
        return ResponseEntity.ok(danhSachBaiHoc);
    }

    @GetMapping("/dang-hoc")
    @Operation(summary = "Lấy danh sách bài học đang học", description = "Lấy danh sách bài học đang học")
    public ResponseEntity<ResponseObject<List<BaiHocTienDoDTO>>> getDanhSachBaiHocDangHoc(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idKhoaHoc") Integer idKhoaHoc) {
        try {
            List<BaiHocTienDoDTO> danhSachBaiHoc = tienDoService.getDanhSachBaiHocTheoTrangThai(idNguoiDung, idKhoaHoc, "dang_hoc");
            return ResponseEntity.ok(new ResponseObject<>(HttpStatus.OK, "Success", danhSachBaiHoc));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    @GetMapping("/bai-hoc-gan-nhat")
    @Operation(summary = "Lấy bài học gần nhất", description = "Lấy bài học gần nhất")
    public ResponseEntity<ResponseObject<BaiHocTienDoDTO>> getBaiHocGanNhat(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idKhoaHoc") Integer idKhoaHoc) {

        BaiHocTienDoDTO baiHoc = tienDoService.getBaiHocGanNhat(idNguoiDung, idKhoaHoc);
        
        if (baiHoc == null) {
            return ResponseEntity.badRequest().body(new ResponseObject<BaiHocTienDoDTO>(HttpStatus.BAD_REQUEST, "Không tìm thấy bài học gần nhất", null));  
        }

        return ResponseEntity.ok(new ResponseObject<BaiHocTienDoDTO>(HttpStatus.OK, "Success", baiHoc));
    }
} 