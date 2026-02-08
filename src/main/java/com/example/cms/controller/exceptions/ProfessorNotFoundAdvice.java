package com.example.cms.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProfessorNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ProfessorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String professorNotFoundHandler(ProfessorNotFoundException ex) {
        return ex.getMessage();
    }
}
