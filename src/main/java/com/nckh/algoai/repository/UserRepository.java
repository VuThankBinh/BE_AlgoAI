package com.nckh.algoai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.nckh.algoai.entity.NguoiDungEntity;

public interface UserRepository extends JpaRepository<NguoiDungEntity, Integer> {
    boolean existsByEmail(String email);
    NguoiDungEntity findByEmail(String email);
    Optional<NguoiDungEntity> findById(Integer id);
}
