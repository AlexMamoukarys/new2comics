package com.example.cms.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.cms.model.entity.Volume;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Long> {

// Add user_id as an additional parameter to search()
    @Query(value = "WITH search AS ( " +
    "    SELECT v.id " +
        "     FROM volumes v " +
        "     WHERE LOWER(v.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
        "        OR LOWER(v.deck) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
    
           "    UNION " +
    
        "     SELECT v.id " +
        "    FROM volumes v " +
        "    JOIN publishers p ON p.id = v.publisherId " +
        "     WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
//        "       OR LOWER(p.deck) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
    
           "    UNION " +
    
        "    SELECT vc.volumeId " +
        "     FROM volumeCharacters vc " +
        "     JOIN characters c ON c.id = vc.characterId " +
        "     WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
//        "       OR LOWER(c.deck) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
    
           "    UNION " +
    
        "    SELECT vg.volumeId " +
        "    FROM volumeGenres vg " +
        "   JOIN genres g ON g.id = vg.genreId " +
        "    WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
        //"       OR LOWER(g.deck) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
    
           "    UNION " +
    
        "   SELECT vt.volumeId " +
        "    FROM volumeTeams vt " +
        "    JOIN teams t ON t.id = vt.teamId " +
        "    WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
//        "       OR LOWER(t.deck) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           ") "+
    
    "SELECT v.*, " +
    "       COUNT(DISTINCT pub.publisherId) " +
           "     + COUNT(DISTINCT pc.characterId) " +
         "     + COUNT(DISTINCT pg.genreId) " +
         "     + COUNT(DISTINCT pt.teamId) AS total_count " +
         "FROM search s " +
    "JOIN volumes v ON v.id = s.id " +
    
    " LEFT JOIN preferred_publishers pub " +
    "    ON v.publisherId = pub.publisherId " +
        "    AND pub.userId = :user_id " +
    
        "LEFT JOIN volumeCharacters vc " +
    "    ON v.id = vc.volumeId " +
        "LEFT JOIN preferred_characters pc " +
    "    ON vc.characterId = pc.characterId " +
        "    AND pc.userId = :user_id " +
    
        "LEFT JOIN volumeGenres vg " +
    "    ON v.id = vg.volumeId " +
    "   LEFT JOIN preferred_genres pg " +
    "    ON vg.genreId = pg.genreId " +
    "    AND pg.userId = :user_id " +
    
    "LEFT JOIN volumeTeams vt " +
    "    ON v.id = vt.volumeId " +
    "LEFT JOIN preferred_teams pt " +
    "    ON vt.teamId = pt.teamId " +
    "    AND pt.userId = :user_id " +
    
    "GROUP BY v.id " +
    "ORDER BY total_count DESC " +
    "LIMIT 15", nativeQuery = true)
  List<Volume> search(@Param("searchTerm") String searchTerm, @Param("user_id") long user_id);

    @Query(value = "SELECT * FROM volumes v ORDER BY v.numLikes desc LIMIT 15", nativeQuery = true)
    List<Volume> retrievePopularVolumes();

    @Modifying
    @Transactional
    @Query(value = "UPDATE volumes SET numLikes = numLikes + 1 WHERE id = :id", nativeQuery = true)
    void incrementLikes(@Param("id") long id); 

    @Modifying
    @Transactional
    @Query(value = "UPDATE volumes SET numLikes = numLikes - 1 WHERE id = :id AND numLikes > 0", nativeQuery = true)
    void decrementLikes(@Param("id") long id);
}



