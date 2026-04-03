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
@Table(name = "publishers")
public class Publisher {

    @Id
    @NotEmpty
    private long id;

    @NotEmpty
    private String name;

//    @Nullable
//    private String deck;

//    @Nullable
//    private String description;
//
//    @NotEmpty
//    private String image;

    //@ManyToMany(mappedBy = "teamId")
    //@Nullable
    //private List<Team> teams = new ArrayList<>();

    //@OneToMany(mappedBy = "publisher")
    //@Nullable
    //private List<Volume> volumes = new ArrayList<>();

}