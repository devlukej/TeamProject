package com.example.Community.domain.repository;

import com.example.Community.domain.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findByUser_Id(String userId);

    @Query("SELECT tr FROM TestResult tr WHERE tr.user.id = :username")
    List<TestResult> findUserResultsByUsername(@Param("username") String username);
}

