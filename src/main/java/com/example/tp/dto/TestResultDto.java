package com.example.tp.dto;

import com.example.tp.domain.entity.Test;
import com.example.tp.domain.entity.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestResultDto {
    private Long id; // TestResult 엔티티의 ID
    private String selectedAnswer; // 사용자가 선택한 답안
    private boolean correct; // 정답 여부
    private UserEntity user;
    private String answer;
    private Test test; // 시험 문제 정보 (TestDto 사용)
    private LocalDateTime TestResultCreatedTime;

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

}