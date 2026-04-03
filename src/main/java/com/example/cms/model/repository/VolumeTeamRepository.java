package com.example.cms.model.repository;

import com.example.cms.model.entity.VolumeTeam;
import com.example.cms.model.entity.VolumeTeamKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolumeTeamRepository extends JpaRepository<VolumeTeam, VolumeTeamKey> {

}
