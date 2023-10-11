package com.example.tp.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "test_result")
public class TestResult extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    private String selectedAnswer;

    private boolean isCorrect;

    public TestResult() {
        // 기본 생성자
    }

    @Builder
    public TestResult(UserEntity user, Test test, String selectedAnswer, boolean isCorrect) {
        this.user = user;
        this.test = test;
        this.selectedAnswer = selectedAnswer;
        this.isCorrect = isCorrect;
    }
}
