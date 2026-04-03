package com.example.cms.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "powers")
public class Power {

    @Id
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String deck;


}