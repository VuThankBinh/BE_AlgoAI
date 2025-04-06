package com.nckh.algoai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.nckh.algoai.entity.TienDoEntity;
import java.util.List;
import java.util.Optional;

@Repository
public interface TienDoRepository extends JpaRepository<TienDoEntity, Integer> {
    Optional<TienDoEntity> findByIdNguoiDungAndIdBaiHoc(Integer idNguoiDung, Integer idBaiHoc);
    
    @Query("SELECT t FROM TienDoEntity t WHERE t.idNguoiDung = :idNguoiDung AND t.baiHoc.idKhoaHoc = :idKhoaHoc")
    List<TienDoEntity> findByIdNguoiDungAndIdKhoaHoc(@Param("idNguoiDung") Integer idNguoiDung, @Param("idKhoaHoc") Integer idKhoaHoc);
    
    @Query("SELECT t FROM TienDoEntity t WHERE t.idNguoiDung = :idNguoiDung AND t.baiHoc.idKhoaHoc = :idKhoaHoc AND t.trangThai = :trangThai")
    List<TienDoEntity> findByIdNguoiDungAndIdKhoaHocAndTrangThai(
            @Param("idNguoiDung") Integer idNguoiDung, 
            @Param("idKhoaHoc") Integer idKhoaHoc,
            @Param("trangThai") String trangThai);
} 