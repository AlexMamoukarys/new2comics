package com.example.cms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "characterTeam")
public class CharacterTeam {

    @EmbeddedId
    CharacterTeamKey characterTeamId;

    @ManyToOne
    @MapsId("characterId")
    @JoinColumn(name = "characterId")
    @JsonIgnoreProperties({"characterTeam"})
    private Character character;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "teamId")
    @JsonIgnoreProperties({"characterTeam"})
    private Team team;

}

