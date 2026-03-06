package com.example.cms.model.repository;

import com.example.cms.model.entity.Power;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerRepository extends JpaRepository<Power, Long> {
}
