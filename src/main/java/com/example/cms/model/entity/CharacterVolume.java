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
@Table(name = "characterVolume")
public class CharacterVolume {

    @EmbeddedId
    CharacterVolumeKey characterVolumeId;

    @ManyToOne
    @MapsId("characterId")
    @JoinColumn(name = "characterId")
    @JsonIgnoreProperties({"characterVolume"})
    private Character character;

    @ManyToOne
    @MapsId("volumeId")
    @JoinColumn(name = "volumeId")
    @JsonIgnoreProperties({"characterVolume"})
    private Volume volume;

}
