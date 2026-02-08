package com.example.cms.controller;

import com.example.cms.controller.exceptions.DepartmentNotFoundException;
import com.example.cms.model.entity.Department;
import com.example.cms.model.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class DepartmentController {
    @Autowired
    private final DepartmentRepository repository;

    public DepartmentController(DepartmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/departments")
    List<Department> retrieveAllDepartments() {
        return repository.findAll();
    }

    @GetMapping("/departments/{code}")
    Department retrieveDepartment(@PathVariable("code") String departCode) {
        return repository.findById(departCode)
                .orElseThrow(() -> new DepartmentNotFoundException(departCode));
    }

}