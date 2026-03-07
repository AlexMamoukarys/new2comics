package com.example.cms.model.repository;

import com.example.cms.model.entity.Publisher;
import com.example.cms.model.entity.Team;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    @Query(value = "select * from publishers s " +
        "where lower(s.name) like lower(concat('%', :searchTerm, '%')) "+
        "or lower(s.deck) like lower(concat('%', :searchTerm, '%'))", nativeQuery = true)
    List<Publisher> search(@Param("searchTerm") String searchTerm);
}
