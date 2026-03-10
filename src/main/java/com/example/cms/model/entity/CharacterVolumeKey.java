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
public class CharacterVolumeKey implements Serializable {

    @Column(name = "characterId")
    Long characterId;

    @Column(name = "volumeId")
    Long volumeId;


    @Override
    public int hashCode() {
        String concatString = String.valueOf(characterId.hashCode()) + String.valueOf(volumeId.hashCode());
        return concatString.hashCode();
    }
    public CharacterVolumeKey(){}

    public CharacterVolumeKey(Long characterId, Long volumeId){
        this.setCharacterId(characterId);
        this.setVolumeId(volumeId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (o == this)
            return true;
        if (!(o instanceof CharacterVolumeKey))
            return false;
        CharacterVolumeKey other = (CharacterVolumeKey) o;
        return characterId.equals(other.characterId) && volumeId.equals(other.volumeId);
    }

}
