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
@Table(name = "volumeCharacters")
public class VolumeCharacter {

    @EmbeddedId
    VolumeCharacterKey volumeCharacterId;

    @ManyToOne
    @MapsId("volumeId")
    @JoinColumn(name = "volumeId")
    @JsonIgnoreProperties({"volumeTeams", "volumeCharacter", "volumeGenre"})
    private Volume volume;

    @ManyToOne
    @MapsId("characterId")
    @JoinColumn(name = "characterId")
    @JsonIgnoreProperties({"volumeCharacter"})
    private Character character;

}

