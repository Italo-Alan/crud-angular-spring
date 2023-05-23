package com.italo.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

//É uma classe Java que não temos métodos setters, só teremos dados através do construtor
public record CourseDTO(
    @JsonProperty Long id,
    @NotNull @Length(min = 2, max = 100) @NotBlank String name,
    @NotNull @Length(max = 10) @Pattern(regexp = "Front-End|Back-End|Full-Stack|Database|DevOps|Database")String category,
    List<LessonDTO> lessons) {
        
}
