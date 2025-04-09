package com.nckh.algoai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nckh.algoai.entity.PhanHoiAiEntity;
import java.util.Optional;

@Repository
public interface PhanHoiAiRepository extends JpaRepository<PhanHoiAiEntity, Integer> {
    Optional<PhanHoiAiEntity> findByIdNopBai(Integer idNopBai);
} 