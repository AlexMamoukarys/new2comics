package com.example.cms.model.repository;

import com.example.cms.model.entity.Preferred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PreferredRepository<P extends Preferred> extends JpaRepository<P, Long> {
}
