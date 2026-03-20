package com.example.cms.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.cms.model.entity.PreferredCharacter;

@Repository
public interface PreferredCharacterRepository extends JpaRepository<PreferredCharacter, Long> {
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM preferred_characters WHERE userId=:userId AND characterId = :charId ", nativeQuery = true)
    void foreignDelete(@PathVariable("char_id") Long charId, @PathVariable("user_id") Long userId);
}
