package com.example.cms.model.repository;

import com.example.cms.model.entity.VolumeGenre;
import com.example.cms.model.entity.VolumeGenreKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolumeGenreRepository extends JpaRepository<VolumeGenre, VolumeGenreKey> {

}
