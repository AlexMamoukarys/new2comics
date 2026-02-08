package com.example.cms.controller.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CourseDto {
    private String code;
    private String name;
    private Long professorId;
}
