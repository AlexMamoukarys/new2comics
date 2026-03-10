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
@Table(name = "characterPower")
public class CharacterPower {

    @EmbeddedId
    CharacterPowerKey characterPowerId;

    @ManyToOne
    @MapsId("characterId")
    @JoinColumn(name = "characterId")
    @JsonIgnoreProperties({"characterPower"})
    private Character character;

    @ManyToOne
    @MapsId("powerId")
    @JoinColumn(name = "powerId")
    @JsonIgnoreProperties({"characterPower"})
    private Power power;

}

