package com.example.cms.model.repository;

import com.example.cms.model.entity.CharacterVolume;
import com.example.cms.model.entity.CharacterVolumeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterVolumeRepository extends JpaRepository<CharacterVolume, CharacterVolumeKey> {

}
