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
@Table(name = "characters")
public class Character {

    @Id
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String deck;

    @NotEmpty
    private String gender;

    @NotEmpty
    private String origin;

    //@ManyToMany(mappedBy = "powerId")
    //@Nullable
    //private List<Power> powers = new ArrayList<>();

    //@ManyToMany(mappedBy = "teamId")
    //@Nullable
    //private List<Team> teams = new ArrayList<>();

    //@ManyToMany(mappedBy = "volumeId")
    //@Nullable
    //private List<Volume> volumes = new ArrayList<>();

}

