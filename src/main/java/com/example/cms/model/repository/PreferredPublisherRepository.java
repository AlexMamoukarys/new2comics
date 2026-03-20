package com.example.cms.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.cms.model.entity.PreferredPublisher;

@Repository
public interface PreferredPublisherRepository extends JpaRepository<PreferredPublisher, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM preferred_publishers WHERE userId=:userId AND publisherId = :publisherId ", nativeQuery = true)
    void foreignDelete(@PathVariable("publisher_id") Long publisherId, @PathVariable("user_id") Long userId);
}
