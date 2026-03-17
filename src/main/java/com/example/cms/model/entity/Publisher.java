package com.example.cms.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.cms.model.entity.Volume;


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

    @Nullable
    private String deck;

    @Nullable
    private String description;

    @NotEmpty
    private String image;

    //@ManyToMany(mappedBy = "teamId")
    //@Nullable
    //private List<Team> teams = new ArrayList<>();

    // @OneToMany(mappedBy = "publisher")
    // @Nullable
    // private List<Volume> volumes = new ArrayList<>();

}