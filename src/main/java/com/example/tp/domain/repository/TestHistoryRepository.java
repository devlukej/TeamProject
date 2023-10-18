package com.example.tp.domain.repository;

import com.example.tp.domain.entity.TestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {
    // 사용자 ID로 특정 사용자의 시험 기록 조회
    List<TestHistory> findByUserId(Long userId);

    List<TestHistory> findByUserIdOrderByCreatedTime(String userId);

}
