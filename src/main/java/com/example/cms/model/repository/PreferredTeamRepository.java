package com.example.cms.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.cms.model.entity.PreferredTeam;

@Repository
public interface PreferredTeamRepository extends JpaRepository<PreferredTeam, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM preferred_teams WHERE userId=:userId AND teamId = :teamId ", nativeQuery = true)
    void foreignDelete(@PathVariable("team_id") Long teamId, @PathVariable("user_id") Long userId);
}
