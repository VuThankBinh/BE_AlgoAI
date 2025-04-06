package com.nckh.algoai.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TienDoDTO {
    private Integer id;
    private Integer idNguoiDung;
    private Integer idBaiHoc;
    private String trangThai;
    private LocalDateTime lanCuoiTruyCap;
} 