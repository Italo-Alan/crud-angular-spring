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
import org.springframework.web.bind.annotation.RestController;

import com.italo.model.Course;
import com.italo.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// Precisa ser adicionado para que as validações(NotNull e Positive) do Java Bean funcionem
@Validated
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

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id){
        //Verifica primeiro se o curso existe
        return courseRepository.findById(id)
            .map(recordFound -> ResponseEntity.ok().body(recordFound))
            .orElse(ResponseEntity.notFound().build());
    }

    //Ele só persiste no banco se estiver tudo válido
    @PostMapping()
    public ResponseEntity<Course> create(@RequestBody @Valid Course course){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(courseRepository.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id,
        @RequestBody @Valid @NotNull @Positive Course course){
        return courseRepository.findById(id)
            .map(recordFound -> {
                recordFound.setName(course.getName());
                recordFound.setCategory(course.getCategory());
                Course updated = courseRepository.save(recordFound);
                return ResponseEntity.ok().body(updated);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id){
        return courseRepository.findById(id)
        .map(recordFound -> {
            courseRepository.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        })
        .orElse(ResponseEntity.notFound().build());
    }

    //Pode ser feito da maneira abaixo também
    // public ResponseEntity<Void> delete(@PathVariable Long id){
    //     return courseRepository.findById(id)
    //     .map(recordFound -> {
    //         courseRepository.deleteById(id);
    //         return ResponseEntity.noContent().<Void>build();
    //     })
    //     .orElse(ResponseEntity.notFound().build());
    // }
}
