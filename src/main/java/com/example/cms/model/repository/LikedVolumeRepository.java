package com.example.cms.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.cms.model.entity.LikedVolume;
import com.example.cms.model.entity.User;
import com.example.cms.model.entity.Volume;

@Repository
public interface LikedVolumeRepository extends JpaRepository<LikedVolume, Long> {
    
    boolean existsByUserAndVolume(User user, Volume volume);
    Optional<LikedVolume> findByUserAndVolume(User user, Volume volume);
    List<LikedVolume> findAllByUserId(long user_id);
}
