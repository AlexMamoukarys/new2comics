package com.example.cms.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "volumes")
public class Volume {

    @Id
    @NotEmpty
    private long id;

    @NotEmpty
    private String name;

    // TODO uncomment and edit column name after making Publisher entity
    // @ManyToOne
    // @JoinColumn(name="___")
    // private Publisher publisher;

    // TODO uncomment and edit column names after making Character and Genre entities
    //@OneToMany(mappedBy = "___")
    //@Nullable
    //private List<Character> characters = new ArrayList<>();
    //
    //@OneToMany(mappedBy = "___")
    //@Nullable
    //private List<Genre> genres = new ArrayList<>();

    // TODO do we want @Nullable for anything else?

    private int numIssues;

    private int numLikes;

    private String summary;

    // TODO uncomment after making Issue entity
    // private Issue firstIssue;

    private int startYear;

    private String image;

}
