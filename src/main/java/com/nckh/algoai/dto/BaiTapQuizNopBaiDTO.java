package com.nckh.algoai.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiTapQuizNopBaiDTO {
    private Integer id;
    private Integer idBaiHoc;
    private String cauHoi;
    private String luaChonA;
    private String luaChonB;
    private String luaChonC;
    private String luaChonD;
    private String dapAnDung;
    private String mucDo;
    private String dapAnNguoiDung;
    private Integer diem;
    private LocalDateTime ngayNop;
} 