package com.example.cms.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // @NotEmpty
    // private String username;

    // @NotEmpty
    // private String password;

    @OneToMany(mappedBy = "user")
    @Nullable
    private List<PreferredGenre> preferredGenres = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Nullable
    private List<PreferredCharacter> preferredCharacters = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Nullable
    private List<PreferredPublisher> preferredPublishers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Nullable
    private List<PreferredPower> preferredPowers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Nullable
    private List<PreferredTeam> preferredTeams = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Nullable
    private List<LikedVolume> likedVolumes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Nullable
    private List<SavedVolume> savedVolumes = new ArrayList<>();

}