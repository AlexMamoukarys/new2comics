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
public class CharacterTeamKey implements Serializable {

    @Column(name = "characterId")
    Long characterId;

    @Column(name = "teamId")
    Long teamId;


    @Override
    public int hashCode() {
        String concatString = String.valueOf(characterId.hashCode()) + String.valueOf(teamId.hashCode());
        return concatString.hashCode();
    }
    public CharacterTeamKey(){}

    public CharacterTeamKey(Long characterId, Long teamId){
        this.setCharacterId(characterId);
        this.setTeamId(teamId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (o == this)
            return true;
        if (!(o instanceof CharacterTeamKey))
            return false;
        CharacterTeamKey other = (CharacterTeamKey) o;
        return characterId.equals(other.characterId) && teamId.equals(other.teamId);
    }

}
