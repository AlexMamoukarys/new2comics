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
    private long id;

    @NotEmpty
    private String name;

    // @ManyToMany(mappedBy = "characterId")
    // @Nullable
    // private List<Character> characters = new ArrayList<>();
    //
    //@OneToMany(mappedBy = "___")
    //@Nullable
    //private List<Genre> genres = new ArrayList<>();

    // TODO do we want @Nullable for anything else?

    private int numIssues;

    private int numLikes;

    private String deck;

//    @OneToOne
//    @JoinColumn(name = "firstIssueId")
//    private Issue firstIssue;


    private int startYear;

    private String image;

    @OneToMany(mappedBy = "volume", cascade = CascadeType.ALL, orphanRemoval = true)
    @Nullable
    private List<VolumeTeam> volumeTeams = new ArrayList<>();

    @OneToMany(mappedBy = "volume", cascade = CascadeType.ALL, orphanRemoval = true)
    @Nullable
    private List<VolumeCharacter> volumeCharacter = new ArrayList<>();

    @OneToMany(mappedBy = "volume", cascade = CascadeType.ALL, orphanRemoval = true)
    @Nullable
    private List<VolumeGenre> volumeGenre = new ArrayList<>();

    // TODO uncomment and edit column name after making Publisher entity
    @ManyToOne
    @JoinColumn(name="publisherId")
    private Publisher publisher;


}
