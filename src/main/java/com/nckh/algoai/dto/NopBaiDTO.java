package com.nckh.algoai.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NopBaiDTO {
    private Integer id;
    private Integer idNguoiDung;
    private Integer idBaiTap;
    private String loaiBaiTap;
    private String dapAn;
    private Integer diem;
    private LocalDateTime ngayNop;
} 