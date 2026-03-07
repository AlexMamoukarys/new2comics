package com.example.cms.controller;

import com.example.cms.controller.dto.CourseDto;
import com.example.cms.controller.exceptions.CourseNotFoundException;
import com.example.cms.controller.exceptions.ProfessorNotFoundException;
import com.example.cms.model.entity.Course;
import com.example.cms.model.entity.Professor;
import com.example.cms.model.repository.CourseRepository;
import com.example.cms.model.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class CourseController {
    @Autowired
    private final CourseRepository repository;

    @Autowired
    private ProfessorRepository professorRepository;

    public CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/courses")
    List<Course> retrieveAllCourses() {
        return repository.findAll();
    }

    @PostMapping("/courses")
    Course createCourse(@RequestBody CourseDto courseDto) {
        Course newCourse = new Course();
        newCourse.setName(courseDto.getName());
        newCourse.setCode(courseDto.getCode());
        Professor professor = professorRepository.findById(courseDto.getProfessorId()).orElseThrow(
                () -> new ProfessorNotFoundException(courseDto.getProfessorId()));
        newCourse.setProfessor(professor);
        return repository.save(newCourse);
    }

    @GetMapping("/courses/{code}")
    Course retrieveCourse(@PathVariable("code") String courseCode) {
        return repository.findById(courseCode)
                .orElseThrow(() -> new CourseNotFoundException(courseCode));
    }

    @PutMapping("/courses/{code}")
    Course updateCourse(@RequestBody CourseDto courseDto, @PathVariable("code") String courseCode) {
        return repository.findById(courseCode)
                .map(course -> {
                    course.setName(courseDto.getName());
                    Professor professor = professorRepository.findById(courseDto.getProfessorId()).orElseThrow(
                            () -> new ProfessorNotFoundException(courseDto.getProfessorId()));
                    course.setProfessor(professor);
                    return repository.save(course);
                })
                .orElseGet(() -> {
                    Course newCourse = new Course();
                    newCourse.setCode(courseCode);
                    Professor professor = professorRepository.findById(courseDto.getProfessorId()).orElseThrow(
                            () -> new ProfessorNotFoundException(courseDto.getProfessorId()));
                    newCourse.setProfessor(professor);
                    return repository.save(newCourse);
                });
    }

    @DeleteMapping("/courses/{code}")
    void deleteCourse(@PathVariable("code") String courseCode) {
        repository.deleteById(courseCode);
    }
}