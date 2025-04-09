package com.nckh.algoai.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bai_hoc")
public class BaiHocEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_khoa_hoc")
    private Integer idKhoaHoc;

    @Column(name = "tieu_de")
    private String tieuDe;

    @Column(name = "noi_dung")
    private String noiDung;

    @Column(name = "muc_do")
    private String mucDo;

    @Column(name = "link_youtube")
    private String linkYoutube;

    @Column(name = "link_mo_ta")
    private String linkMoTa;

    @Column(name = "anh_bai_hoc")
    private String anhBaiHoc;

    @ManyToOne
    @JoinColumn(name = "id_khoa_hoc", insertable = false, updatable = false)
    private KhoaHocEntity course;
}
