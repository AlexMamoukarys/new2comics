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
@Table(name = "teams")
public class Team {

    @Id
    @NotEmpty
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String deck;

    //@ManyToMany(mappedBy = "__")
    //@Nullable
    //private List<Volume> volumes = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @Nullable
    private List<VolumeTeam> volumeTeams = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="publisherId")
    private Publisher publisher;
}
