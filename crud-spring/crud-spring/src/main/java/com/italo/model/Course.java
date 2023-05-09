package com.italo.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Length;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE Course SET status = 'Inactive' WHERE id = ?")
@Where(clause = "status = 'Active'") //Hibernate faz a adição do filtro na clausula where e sempre procura pelo status como activated
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private Long id;

    // @Column(name = "nome") É usado quando o nome da coluna que declaramos aqui é diferente da que temos no BD.
    @NotNull //Não permite que seja vazio
    @NotBlank //Permite um caracter que não seja um espaço
    @Length(min = 2, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull
    @Length(max = 10)
    @Pattern(regexp = "Front-End|Back-End|Full-Stack|Database|DevOps|Database")
    @Column(length = 20, nullable = false)
    private String category;
    
    @NotNull
    @Length(max = 10)
    @Pattern(regexp = "Active|Inactive")
    @Column(length = 15, nullable = false)
    private String status = "Active";
}
