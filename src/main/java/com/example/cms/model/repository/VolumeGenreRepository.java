package com.example.cms.model.repository;

import com.example.cms.model.entity.VolumeGenre;
import com.example.cms.model.entity.VolumeGenreKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface VolumeGenreRepository extends JpaRepository<VolumeGenre, VolumeGenreKey> {

}
