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

    @NotEmpty
    private int numIssues;
    
    // commented out to test volume.sql (need to add num likes to sql or change this to nullable)
     private int numLikes;

    @Nullable
    // changed column type for deck to allow long length
    @Column(columnDefinition = "TEXT")
    private String deck;

    // uncommneted firstIssue and renamed column to firstIssue
    @OneToOne
    @JoinColumn(name = "firstIssue")
    private Issue firstIssue;

    @NotEmpty
    private int startYear;

    @NotEmpty
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
