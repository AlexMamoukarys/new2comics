package com.example.cms.model.repository;

import com.example.cms.model.entity.Volume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Long> {
    
    @Query(value = "select * from volumes s " +
        "where lower(s.name) like lower(concat('%', :searchTerm, '%')) "+
        "or lower(s.deck) like lower(concat('%', :searchTerm, '%'))", nativeQuery = true)
    List<Volume> search(@Param("searchTerm") String searchTerm);

    @Modifying
    @Transactional
    @Query(value = "UPDATE volumes SET numLikes = numLikes + 1 WHERE id = :id", nativeQuery = true)
    void incrementLikes(@Param("id") long id);  
}
