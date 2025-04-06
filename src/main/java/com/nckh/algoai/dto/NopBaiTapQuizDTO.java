package com.nckh.algoai.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NopBaiTapQuizDTO {
    private Integer idNguoiDung;
    private Integer idBaiHoc;
    private String dapAn;
    private String mucDo;
    private Integer diem;
    private LocalDateTime ngayNop;
}
