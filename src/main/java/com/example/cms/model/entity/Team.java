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
@Table(name = "teams")
public class Team {

    @Id
    @NotEmpty
    private long id;

    @NotEmpty
    private String name;

//    @NotEmpty
//    private String deck;

    //@ManyToMany(mappedBy = "__")
    //@Nullable
    //private List<Volume> volumes = new ArrayList<>();

//    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Nullable
//    private List<VolumeTeam> volumeTeams = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name="publisherId")
//    private Publisher publisher;
}
