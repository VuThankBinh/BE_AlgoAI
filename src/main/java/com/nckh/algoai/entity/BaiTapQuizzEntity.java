package com.nckh.algoai.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bai_tap_quizz")
public class BaiTapQuizzEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_bai_hoc")
    private Integer idBaiHoc;

    @Column(name = "cau_hoi")
    private String cauHoi;

    @Column(name = "lua_chon_a")
    private String luaChonA;

    @Column(name = "lua_chon_b")
    private String luaChonB;

    @Column(name = "lua_chon_c")
    private String luaChonC;

    @Column(name = "lua_chon_d")
    private String luaChonD;

    @Column(name = "dap_an_dung")
    private String dapAnDung;

    @Column(name = "muc_do")
    private String mucDo;

    @ManyToOne
    @JoinColumn(name = "id_bai_hoc", insertable = false, updatable = false)
    private BaiHocEntity baiHoc;
} 