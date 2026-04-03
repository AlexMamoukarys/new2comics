package com.example.cms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "volumeTeams")
public class VolumeTeam {

    @EmbeddedId
    VolumeTeamKey volumeTeamId;

    @ManyToOne
    @MapsId("volumeId")
    @JoinColumn(name = "volumeId")
    @JsonIgnoreProperties({"volumeTeams", "volumeCharacter", "volumeGenre"})
    private Volume volume;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "teamId")
    @JsonIgnoreProperties({"volumeTeams"})
    private Team team;

}

