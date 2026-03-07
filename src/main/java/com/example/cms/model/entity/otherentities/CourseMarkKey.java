package com.example.cms.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class CourseMarkKey implements Serializable {

    @Column(name = "studentId")
    Long studentId;

    @Column(name = "courseId")
    String courseId;


    @Override
    public int hashCode() {
        String concatString = String.valueOf(studentId.hashCode()) + String.valueOf(courseId.hashCode());
        return concatString.hashCode();
    }
    public CourseMarkKey(){}

    public CourseMarkKey(Long studentId, String courseId){
        this.setStudentId(studentId);
        this.setCourseId(courseId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (o == this)
            return true;
        if (!(o instanceof CourseMarkKey))
            return false;
        CourseMarkKey other = (CourseMarkKey) o;
        return studentId.equals(other.studentId) && courseId.equals(other.courseId);
    }

}
