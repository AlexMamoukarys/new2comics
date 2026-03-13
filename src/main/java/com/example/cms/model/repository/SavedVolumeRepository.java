package com.example.cms.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.cms.model.entity.SavedVolume;
import com.example.cms.model.entity.User;
import com.example.cms.model.entity.Volume;

@Repository
public interface SavedVolumeRepository extends JpaRepository<SavedVolume, Long> {
     
    boolean existsByUserAndVolume(User user, Volume volume);
    Optional<SavedVolume> findByUserAndVolume(User user, Volume volume);
    List<SavedVolume> findAllByUserId(long user_id);
}

