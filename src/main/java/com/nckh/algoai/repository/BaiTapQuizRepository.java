package com.nckh.algoai.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nckh.algoai.entity.BaiTapQuizzEntity;

@Repository
public interface BaiTapQuizRepository extends JpaRepository<BaiTapQuizzEntity, Integer> {
    List<BaiTapQuizzEntity> findByMucDoAndIdBaiHoc(String mucDo, Integer idBaiHoc);
    List<BaiTapQuizzEntity> findByIdBaiHoc(Integer idBaiHoc);
} 