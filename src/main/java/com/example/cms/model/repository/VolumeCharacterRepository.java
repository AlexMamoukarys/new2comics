package com.example.cms.model.repository;

import com.example.cms.model.entity.VolumeCharacter;
import com.example.cms.model.entity.VolumeCharacterKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolumeCharacterRepository extends JpaRepository<VolumeCharacter, VolumeCharacterKey> {

}
