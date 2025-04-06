package com.nckh.algoai.service;

import java.util.List;
import com.nckh.algoai.entity.KhoaHocEntity;
import com.nckh.algoai.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    public List<KhoaHocEntity> getAllCourses() {
        return courseRepository.getAllCourses();
    }
} 