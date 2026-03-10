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
public class PublisherTeamKey implements Serializable {

    @Column(name = "publisherId")
    Long publisherId;

    @Column(name = "teamId")
    Long teamId;


    @Override
    public int hashCode() {
        String concatString = String.valueOf(publisherId.hashCode()) + String.valueOf(teamId.hashCode());
        return concatString.hashCode();
    }
    public PublisherTeamKey(){}

    public PublisherTeamKey(Long publisherId, Long teamId){
        this.setPublisherId(publisherId);
        this.setTeamId(teamId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (o == this)
            return true;
        if (!(o instanceof PublisherTeamKey))
            return false;
        PublisherTeamKey other = (PublisherTeamKey) o;
        return publisherId.equals(other.publisherId) && teamId.equals(other.teamId);
    }

}
