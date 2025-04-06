package com.nckh.algoai.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bai_tap_code")
public class BaiTapCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_bai_hoc")
    private Integer idBaiHoc;

    @Column(name = "de_bai")
    private String deBai;

    @Column(name = "dau_vao_mau")
    private String dauVaoMau;

    @Column(name = "dau_ra_mau")
    private String dauRaMau;

    @Column(name = "muc_do")
    private String mucDo;

    @ManyToOne
    @JoinColumn(name = "id_bai_hoc", insertable = false, updatable = false)
    private BaiHocEntity baiHoc;
} 