package com.italo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.italo.model.Course;
import com.italo.repository.CourseRepository;
import com.italo.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// Precisa ser adicionado para que as validações(NotNull e Positive) do Java Bean funcionem
@Validated
@RestController
@RequestMapping("/api/courses")
public class CursosController {

    private final CourseService courseService;

    public CursosController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public @ResponseBody List<Course> list(){
        return courseService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id){
        //Verifica primeiro se o curso existe
        return courseService.findById(id)
            .map(recordFound -> ResponseEntity.ok().body(recordFound))
            .orElse(ResponseEntity.notFound().build());
    }

    //Ele só persiste no banco se estiver tudo válido
    @PostMapping()
    public Course create(@RequestBody @Valid Course course){
        return courseService.create(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable @NotNull @Positive Long id, 
            @RequestBody @Valid  Course course){
        return courseService.update(id, course)
            .map(recordFound -> ResponseEntity.ok().body(recordFound))
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id){
        if(courseService.delete(id)){
            return ResponseEntity.noContent().<Void>build();
        }

        return ResponseEntity.notFound().build();
    }

}
