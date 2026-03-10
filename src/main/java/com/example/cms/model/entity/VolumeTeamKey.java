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
public class VolumeTeamKey implements Serializable {

    @Column(name = "volumeId")
    Long volumeId;

    @Column(name = "teamId")
    Long teamId;


    @Override
    public int hashCode() {
        String concatString = String.valueOf(volumeId.hashCode()) + String.valueOf(teamId.hashCode());
        return concatString.hashCode();
    }
    public VolumeTeamKey(){}

    public VolumeTeamKey(Long volumeId, Long teamId){
        this.setVolumeId(volumeId);
        this.setTeamId(teamId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (o == this)
            return true;
        if (!(o instanceof VolumeTeamKey))
            return false;
        VolumeTeamKey other = (VolumeTeamKey) o;
        return volumeId.equals(other.volumeId) && teamId.equals(other.teamId);
    }

}
