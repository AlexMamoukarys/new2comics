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
public class CharacterPowerKey implements Serializable {

    @Column(name = "characterId")
    Long characterId;

    @Column(name = "powerId")
    Long powerId;


    @Override
    public int hashCode() {
        String concatString = String.valueOf(characterId.hashCode()) + String.valueOf(powerId.hashCode());
        return concatString.hashCode();
    }
    public CharacterPowerKey(){}

    public CharacterPowerKey(Long characterId, Long powerId){
        this.setCharacterId(characterId);
        this.setPowerId(powerId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (o == this)
            return true;
        if (!(o instanceof CharacterPowerKey))
            return false;
        CharacterPowerKey other = (CharacterPowerKey) o;
        return characterId.equals(other.characterId) && powerId.equals(other.powerId);
    }

}
