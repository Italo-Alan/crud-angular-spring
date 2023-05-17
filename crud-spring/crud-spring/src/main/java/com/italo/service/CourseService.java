package com.italo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.italo.dto.CourseDTO;
import com.italo.dto.mapper.CourseMapper;
import com.italo.exception.RecordNotFoundException;
import com.italo.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper){
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public  List<CourseDTO> list(){
        return courseRepository.findAll()
            .stream()
            .map(courseMapper::toDTO)
            .collect(Collectors.toList());
    }

    public CourseDTO findById(@NotNull @Positive Long id){
        return courseRepository.findById(id).map(courseMapper::toDTO)
            .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course){
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid CourseDTO course){
    return courseRepository.findById(id)
        .map(recordFound -> {
            recordFound.setName(course.name());
            recordFound.setCategory(this.courseMapper.convertCategoryValue(course.category()));
            return courseRepository.save(recordFound);
        }).map(courseMapper::toDTO).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id){
        courseRepository.delete(courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));

        //Pode ser feita assim
        // return courseRepository.findById(id)
        // .map(recordFound -> {
        //     courseRepository.deleteById(id);
        //     return true;
        // })
        // .orElse(false);
    }
}
