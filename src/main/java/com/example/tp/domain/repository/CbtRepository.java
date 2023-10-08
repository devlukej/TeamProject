package com.example.tp.domain.repository;

import com.example.tp.domain.entity.CbtEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CbtRepository extends JpaRepository<CbtEntity, Integer> {

    List<CbtEntity> findAll();
}
