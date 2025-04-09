package com.nckh.algoai.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BaiHocTienDoDTO {
    private Integer id;
    private Integer idKhoaHoc;
    private String tieuDe;
    private String noiDung;
    private String mucDo;
    private String linkYoutube;
    private String linkMoTa;
    private String trangThai;
    private String anhBaiHoc;
    private LocalDateTime lanCuoiTruyCap;
    private Boolean daLamQuiz;
    private Integer diemQuiz;
    private String mucDoQuiz;
    private Boolean daLamCode;
    private Integer diemCode;
    private String mucDoCode;
} 