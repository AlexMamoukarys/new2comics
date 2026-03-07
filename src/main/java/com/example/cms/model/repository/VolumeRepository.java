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
    @Query(value = "select distinct * from volumes v "
                //+ "join characters c on v.character_id = c.id"
                //+ "join genres g on v.genre_id = g.id"
                //+ "join publisher p on v.id = p.volume_id"
                //+ "join teams t on p.team_id = t.id"
                //+ "join issues s on v.id = s.volume_id"
                + "where lower(v.name) like lower(concat('%', :searchTerm, '%'))"
                //+ "or c.name like %:searchTerm%"
                //+ "or g.name like %:searchTerm%"
                //+ "or p.name like %:searchTerm%"
                //+ "or t.name like %:searchTerm%"
                //+ "or s.name like %:searchTerm%"
                    , nativeQuery = true)
    List<Volume> search(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT * FROM volumes v ORDER BY v.numLikes desc", nativeQuery = true)
    List<Volume> retrievePopularVolumes();

//    @Query(value = "select distinct v.* from volumes v"
//            + "join characters c on v.character_id = c.id"
//            + "join genres g on v.genre_id = g.id"
//            + "join publisher p on v.id = p.volume_id"
//            + "join teams t on p.team_id = t.id"
//            + "join issues s on v.id = s.volume_id"
//            + "where v.name like %:searchTerm%"
//            + "or c.name like %:searchTerm%"
//            + "or g.name like %:searchTerm%"
//            + "or p.name like %:searchTerm%"
//            + "or t.name like %:searchTerm%"
//            + "or s.name like %:searchTerm%", nativeQuery = true)
//    List<Volume> search(@Param("searchTerm") String searchTerm);


    @Modifying
    @Transactional
    @Query(value = "UPDATE volumes SET numLikes = numLikes + 1 WHERE id = :id", nativeQuery = true)
    void incrementLikes(@Param("id") long id);  
}
