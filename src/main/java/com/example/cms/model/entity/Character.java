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
@Table(name = "characters")
public class Character {

    @Id
    @NotEmpty
    private long id;

    @NotEmpty
    private String name;

    //@ManyToMany(mappedBy = "powerId")
    //@Nullable
    //private List<Power> powers = new ArrayList<>();

    //@ManyToMany(mappedBy = "teamId")
    //@Nullable
    //private List<Team> teams = new ArrayList<>();
}

