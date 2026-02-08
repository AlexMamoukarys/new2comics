package com.example.cms.controller;

import com.example.cms.controller.exceptions.ProfessorNotFoundException;
import com.example.cms.model.entity.Professor;
import com.example.cms.model.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ProfessorController {
    @Autowired
    private final ProfessorRepository repository;

    public ProfessorController(ProfessorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/professors")
    List<Professor> retrieveAllProfessors() {
        return repository.findAll();
    }

    @PostMapping("/professors")
    Professor createProfessor(@RequestBody Professor newProfessor) {
        newProfessor.setSalary(Math.max(newProfessor.getSalary(), 30000));
        return repository.save(newProfessor);
    }

    @GetMapping("/professors/{id}")
    Professor retrieveProfessor(@PathVariable("id") Long professorId) {
        return repository.findById(professorId)
                .orElseThrow(() -> new ProfessorNotFoundException(professorId));
    }

    @PutMapping("/professors/{id}")
    Professor updateProfessor(@RequestBody Professor newProfessor, @PathVariable("id") Long professorId) {

        return repository.findById(professorId)
                .map(professor -> {
                    professor.setFirstName(newProfessor.getFirstName());
                    professor.setLastName(newProfessor.getLastName());
                    professor.setSalary(Math.max(newProfessor.getSalary(), 30000));
                    return repository.save(professor);
                })
                .orElseGet(() -> {
                    newProfessor.setId(professorId);
                    newProfessor.setSalary(Math.max(newProfessor.getSalary(), 30000));
                    return repository.save(newProfessor);
                });
    }

    @DeleteMapping("/professors/{id}")
    void deleteProfessor(@PathVariable("id") Long professorId) {
        repository.deleteById(professorId);
    }
}
