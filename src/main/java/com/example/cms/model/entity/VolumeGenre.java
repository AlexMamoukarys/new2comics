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
@Table(name = "volumeGenres")
public class VolumeGenre {

    @EmbeddedId
    VolumeGenreKey volumeGenreId;

    @ManyToOne
    @MapsId("volumeId")
    @JoinColumn(name = "volumeId")
    @JsonIgnoreProperties({"volumeTeams", "volumeCharacter", "volumeGenre"})
    private Volume volume;

    @ManyToOne
    @MapsId("genreId")
    @JoinColumn(name = "genreId")
    @JsonIgnoreProperties({"volumeGenre"})
    private Genre genre;

}

