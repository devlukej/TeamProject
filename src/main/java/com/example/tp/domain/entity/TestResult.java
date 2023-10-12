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

    private String answer;

    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "history_id")
    private TestHistory testHistory; // history_id와의 관계

    public TestResult() {
        // 기본 생성자
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Builder
    public TestResult(UserEntity user, Test test, String selectedAnswer, boolean isCorrect) {
        this.user = user;
        this.test = test;
        this.selectedAnswer = selectedAnswer;
        this.isCorrect = isCorrect;
    }
}