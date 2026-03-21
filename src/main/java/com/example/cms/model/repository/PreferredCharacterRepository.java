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
import org.springframework.stereotype.Repository;

@Repository
public interface PreferredCharacterRepository extends PreferredRepository<PreferredCharacter> { }
