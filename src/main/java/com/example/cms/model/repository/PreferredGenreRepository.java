package com.example.cms.model.repository;

import java.util.List;

import com.example.cms.model.entity.Genre;
import com.example.cms.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.cms.model.entity.PreferredGenre;

@Repository
public interface PreferredGenreRepository extends JpaRepository<PreferredGenre, Long> {
    boolean existsByUserAndGenre(User user, Genre genre);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM preferred_genres WHERE userId=:userId AND genreId = :genreId ", nativeQuery = true)
    void foreignDelete(@PathVariable("genre_id") Long genreId, @PathVariable("user_id") Long userId);
}
