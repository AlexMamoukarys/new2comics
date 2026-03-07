package com.example.cms.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "classrooms")
public class Classroom {
    @Id
    @NotEmpty
    private String code;

    @NotEmpty
    private Integer capacity;

    public Classroom(String code, Integer capacity){
        this.code = code;
        this.capacity = capacity;
    }
}
