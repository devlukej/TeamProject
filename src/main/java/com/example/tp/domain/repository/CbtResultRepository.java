package com.example.tp.domain.repository;

import com.example.tp.domain.entity.CbtResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CbtResultRepository extends JpaRepository<CbtResultEntity, Long> {

}