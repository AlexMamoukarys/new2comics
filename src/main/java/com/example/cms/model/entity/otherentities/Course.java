package com.example.cms.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "courses")
public class Course {

    @Id
    @NotEmpty
    private String code;

    @NotEmpty
    private String name;

    @ManyToOne
    @JoinColumn(name="professorId")
    private Professor professor;

    @OneToMany(mappedBy = "course")
    @Nullable
    private List<CourseMark> marks = new ArrayList<>();

    public Course(String code, String name, Professor professor){
        this.code = code;
        this.name = name;
        this.professor = professor;
    }

}
