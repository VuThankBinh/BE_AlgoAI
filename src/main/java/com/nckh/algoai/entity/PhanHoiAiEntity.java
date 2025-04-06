package com.nckh.algoai.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "phan_hoi_ai")
public class PhanHoiAiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_nopbai")
    private Integer idNopBai;

    @Column(name = "noi_dung")
    private String noiDung;

    @Column(name = "goi_y_cai_thien")
    private String goiYCaiThien;

    @ManyToOne
    @JoinColumn(name = "id_nopbai", insertable = false, updatable = false)
    private NopBaiEntity nopBai;
} 