package com.nckh.algoai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nckh.algoai.entity.TienDoEntity;
import com.nckh.algoai.service.TienDoService;
import com.nckh.algoai.dto.BaiHocTienDoDTO;
import java.util.List;

@RestController
@RequestMapping("/api/tien-do")
public class TienDoController {

    @Autowired
    private TienDoService tienDoService;

    @PostMapping("/cap-nhat")
    public ResponseEntity<TienDoEntity> capNhatTienDo(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idBaiHoc") Integer idBaiHoc) {
        TienDoEntity tienDo = tienDoService.capNhatTienDo(idNguoiDung, idBaiHoc);
        return ResponseEntity.ok(tienDo);
    }
    
    @GetMapping("/bai-hoc")
    public ResponseEntity<List<BaiHocTienDoDTO>> getDanhSachBaiHocTheoTrangThai(
            @RequestParam("idNguoiDung") Integer idNguoiDung,
            @RequestParam("idKhoaHoc") Integer idKhoaHoc,
            @RequestParam(value = "trangThai", required = false) String trangThai) {
        List<BaiHocTienDoDTO> danhSachBaiHoc = tienDoService.getDanhSachBaiHocTheoTrangThai(idNguoiDung, idKhoaHoc, trangThai);
        return ResponseEntity.ok(danhSachBaiHoc);
    }
} 