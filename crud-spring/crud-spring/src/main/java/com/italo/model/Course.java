package com.italo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // @JsonProperty("_id")
    private Long id;

    // @Column(name = "nome") É usado quando o nome da coluna que declaramos aqui é diferente da que temos no BD.
    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 15, nullable = false)
    private String category;
}
