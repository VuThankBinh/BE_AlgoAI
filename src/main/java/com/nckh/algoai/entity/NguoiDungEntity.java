package com.nckh.algoai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "nguoi_dung")
public class NguoiDungEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten_dang_nhap")
    private String tenDangNhap;

    @Column(name = "email")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "anh_dai_dien")
    private String anhDaiDien;

}
