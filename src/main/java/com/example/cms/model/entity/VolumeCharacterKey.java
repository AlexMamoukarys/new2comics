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
public class VolumeCharacterKey implements Serializable {

    @Column(name = "volumeId")
    Long volumeId;

    @Column(name = "characterId")
    Long characterId;


    @Override
    public int hashCode() {
        String concatString = String.valueOf(volumeId.hashCode()) + String.valueOf(characterId.hashCode());
        return concatString.hashCode();
    }
    public VolumeCharacterKey(){}

    public VolumeCharacterKey(Long volumeId, Long characterId){
        this.setVolumeId(volumeId);
        this.setCharacterId(characterId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (o == this)
            return true;
        if (!(o instanceof VolumeCharacterKey))
            return false;
        VolumeCharacterKey other = (VolumeCharacterKey) o;
        return volumeId.equals(other.volumeId) && characterId.equals(other.characterId);
    }

}
