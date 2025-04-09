package com.nckh.algoai.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nckh.algoai.entity.KhoaHocEntity;
import com.nckh.algoai.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course Controller", description = "API get danh sách khóa học")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<KhoaHocEntity>> getAllCourses() {
        List<KhoaHocEntity> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
} 