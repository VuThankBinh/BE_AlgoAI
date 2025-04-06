package com.nckh.algoai.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "nop_bai")
public class NopBaiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_nguoi_dung")
    private Integer idNguoiDung;

    @Column(name = "id_bai_hoc")
    private Integer idBaiHoc;

    @Column(name = "loai_bai_tap")
    private String loaiBaiTap;

    @Column(name="muc_do")
    private String mucDo;

    @Column(name = "dap_an")
    private String dapAn;

    @Column(name = "diem")
    private Integer diem;

    @Column(name = "ngay_nop")
    private LocalDateTime ngayNop;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_dung", insertable = false, updatable = false)
    private NguoiDungEntity user;
} 