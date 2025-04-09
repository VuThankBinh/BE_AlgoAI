package com.nckh.algoai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nckh.algoai.entity.NopBaiEntity;
import java.util.List;

@Repository
public interface NopBaiRepository extends JpaRepository<NopBaiEntity, Integer> {
    List<NopBaiEntity> findByIdNguoiDungAndIdBaiHoc(Integer idNguoiDung, Integer idBaiHoc);
    List<NopBaiEntity> findByIdNguoiDungAndLoaiBaiTap(Integer idNguoiDung, String loaiBaiTap);
    List<NopBaiEntity> findByIdNguoiDungAndIdBaiHocAndLoaiBaiTap(Integer idNguoiDung, Integer idBaiHoc, String loaiBaiTap);
} 