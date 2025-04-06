package com.nckh.algoai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nckh.algoai.entity.KhoaHocEntity;

@Repository
public interface CourseRepository extends JpaRepository<KhoaHocEntity, Integer> {
    @Query("SELECT c FROM KhoaHocEntity c")
    List<KhoaHocEntity> getAllCourses();
} 