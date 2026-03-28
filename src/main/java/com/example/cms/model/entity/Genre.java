package com.example.cms.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.cms.model.entity.Volume;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "genres")
public class Genre {

    @Id
    private long id;

    @NotEmpty
    private String name;

    // @Nullable

//    @Nullable
//    @Column(name = "aliases", columnDefinition = "TEXT")
//    private String aliases;
//
//    @Nullable
//    @Column(name = "api_detail_url")
//    private String apiDetailUrl;
//
//    @Nullable
//    @Column(name = "count_of_isssue_appearances")
//    private Integer countOfIssueAppearances;
//
//    @Nullable
//    @Column(name = "date_added")
//    private String dateAdded;
//
//    @Nullable
//    @Column(name = "date_last_updated")
//    private String dateLastUpdated;
//
//    @Nullable
//    @Column(name = "deck", columnDefinition = "TEXT")
//    private String deck;
//
//    @Nullable
//    @Column(name = "description", columnDefinition = "TEXT")
//    private String description;
//
//    @Nullable
//    @Column(name = "first_appeared_in_issue", columnDefinition = "TEXT")
//    private String firstAppearedInIssue;
//
//    @Nullable
//    @Column(name = "image", columnDefinition = "TEXT")
//    private String image;
//
//    @Nullable
//    @Column(name = "site_detail_url")
//    private String siteDetailUrl;
//
//    @Nullable
//    @Column(name = "start_year")
//    private Integer startYear;




    
}

