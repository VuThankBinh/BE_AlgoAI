package com.nckh.algoai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nckh.algoai.entity.BaiHocEntity;
import java.util.List;

@Repository
public interface BaiHocRepository extends JpaRepository<BaiHocEntity, Integer> {
    List<BaiHocEntity> findByIdKhoaHoc(Integer idKhoaHoc);
} 