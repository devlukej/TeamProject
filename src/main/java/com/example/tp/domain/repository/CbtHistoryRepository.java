package com.example.tp.domain.repository;

import com.example.tp.domain.entity.CbtHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CbtHistoryRepository extends JpaRepository<CbtHistoryEntity, Long> {

}
