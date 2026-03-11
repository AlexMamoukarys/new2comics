package com.example.cms.model.repository;

import com.example.cms.model.entity.Genre;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query(value = "select * from genres s " +
        "where lower(s.name) like lower(concat('%', :searchTerm, '%')) "+
        "or lower(s.deck) like lower(concat('%', :searchTerm, '%'))", nativeQuery = true)
    List<Genre> search(@Param("searchTerm") String searchTerm);

    @Modifying
    @Transactional
    @Query("UPDATE genres g SET g.name = :name WHERE g.id = :id")
    void changeName(@Param("id") Long id, @Param("name") String name);
}
