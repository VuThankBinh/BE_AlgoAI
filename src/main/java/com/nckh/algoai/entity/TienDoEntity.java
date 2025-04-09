package com.nckh.algoai.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tien_do")
public class TienDoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_nguoi_dung")
    private Integer idNguoiDung;

    @Column(name = "id_bai_hoc")
    private Integer idBaiHoc;

    @Column(name = "trang_thai")
    private String trangThai;

    @Column(name = "lan_cuoi_truy_cap")
    private LocalDateTime lanCuoiTruyCap;


    @ManyToOne
    @JoinColumn(name = "id_nguoi_dung", insertable = false, updatable = false)
    private NguoiDungEntity nguoiDung;

    @ManyToOne
    @JoinColumn(name = "id_bai_hoc", insertable = false, updatable = false)
    private BaiHocEntity baiHoc;
} 