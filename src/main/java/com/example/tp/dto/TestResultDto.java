package com.example.tp.dto;

import lombok.Data;

@Data
public class TestResultDto {
    private Long id; // TestResult 엔티티의 ID
    private String selectedAnswer; // 사용자가 선택한 답안
    private boolean isCorrect; // 정답 여부
    private TestDto test; // 시험 문제 정보 (TestDto 사용)
}

