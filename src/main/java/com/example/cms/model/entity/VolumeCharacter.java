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

