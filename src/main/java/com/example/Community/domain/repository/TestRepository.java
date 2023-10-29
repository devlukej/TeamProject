package com.example.Community.domain.repository;

import com.example.Community.domain.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// TestRepository.java
@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByNameAndYearAndSubject(String name, String year, String subject);


}
