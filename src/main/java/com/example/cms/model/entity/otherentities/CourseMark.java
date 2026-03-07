package com.example.cms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "marks")
public class CourseMark {

    @EmbeddedId
    CourseMarkKey markId;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "studentId")
    @JsonIgnoreProperties({"marks"})
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "courseCode")
    @JsonIgnoreProperties({"marks"})
    private Course course;

    @NotNull
    private int mark;


}

