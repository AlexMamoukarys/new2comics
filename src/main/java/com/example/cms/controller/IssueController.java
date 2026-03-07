package com.example.cms.controller;

import com.example.cms.controller.exceptions.IssueNotFoundException;
import com.example.cms.model.entity.Issue;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.IssueRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin // consider restricting in prod
@RestController
public class IssueController {

    private final IssueRepository repository;

    public IssueController(IssueRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/issues")
    public List<Issue> retrieveAllIssues() {
        return repository.findAll();
    }

    @PostMapping("/issues")
    public Issue createIssue(@RequestBody Issue newIssue) {
        return repository.save(newIssue);
    }

    @GetMapping("/issues/{id}")
    public Issue retrieveIssue(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new IssueNotFoundException(id));
    }

    @DeleteMapping("/issues/{id}")
    public void deleteIssue(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/issues/search/{searchstring}")
    List<Issue> searchIssues(@PathVariable("searchstring") String searchString) {
        return repository.search(searchString);
    }

}