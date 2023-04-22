package com.italo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.italo.model.Course;
import com.italo.repository.CourseRepository;

@RestController
@RequestMapping("/api/courses")
public class CursosController {

    private final CourseRepository courseRepository;

    public CursosController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping()
    public List<Course> list(){
        return courseRepository.findAll();
    }
}
