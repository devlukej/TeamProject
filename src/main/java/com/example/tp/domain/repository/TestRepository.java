package com.example.tp.domain.repository;

import com.example.tp.domain.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// TestRepository.java
@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByNameAndYearAndType(String name, String year, String type);


}
