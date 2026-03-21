package com.example.cms.model.repository;

import com.example.cms.model.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface PropertyRepository<P extends Property> extends JpaRepository<P, Long> {
    List<P> search(String searchTerm);
}
