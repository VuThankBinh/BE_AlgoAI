package com.nckh.algoai.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nckh.algoai.entity.BaiTapCodeEntity;

@Repository
public interface BaiTapCodeRepository extends JpaRepository<BaiTapCodeEntity, Integer> {
    List<BaiTapCodeEntity> findByIdBaiHocAndMucDo(Integer idBaiHoc, String mucDo);
    List<BaiTapCodeEntity> findByIdBaiHoc(Integer idBaiHoc);
} 