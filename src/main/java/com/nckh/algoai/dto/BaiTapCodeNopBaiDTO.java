package com.nckh.algoai.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiTapCodeNopBaiDTO {
    private Integer id;
    private Integer idBaiHoc;
    private String deBai;
    private String dauVaoMau;
    private String dauRaMau;
    private String mucDo;
    private String dapAnNguoiDung;
    private Integer diem;
    private LocalDateTime ngayNop;
} 