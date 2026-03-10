package com.example.cms.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class VolumeGenreKey implements Serializable {

    @Column(name = "volumeId")
    Long volumeId;

    @Column(name = "genreId")
    Long genreId;


    @Override
    public int hashCode() {
        String concatString = String.valueOf(volumeId.hashCode()) + String.valueOf(genreId.hashCode());
        return concatString.hashCode();
    }
    public VolumeGenreKey(){}

    public VolumeGenreKey(Long volumeId, Long genreId){
        this.setVolumeId(volumeId);
        this.setGenreId(genreId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (o == this)
            return true;
        if (!(o instanceof VolumeGenreKey))
            return false;
        VolumeGenreKey other = (VolumeGenreKey) o;
        return volumeId.equals(other.volumeId) && genreId.equals(other.genreId);
    }

}
