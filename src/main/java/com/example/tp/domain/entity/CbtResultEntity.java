package com.example.tp.domain.entity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "cbt_result")
public class CbtResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "test_num")
    private Test test;

    @Column(name = "selected_answer")
    private int selectedAnswer;

    @Column(name = "is_correct")
    private boolean correct;

    @Column(name = "earned_score") // earnedScore 필드 추가
    private int earnedScore;

    @Column(name = "exam_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date examDate;

    @Builder
    public CbtResultEntity(UserEntity user, Test test, int selectedAnswer, boolean correct, Date examDate, int earnedScore) {
        this.user = user;
        this.test = test;
        this.selectedAnswer = selectedAnswer;
        this.correct = correct;
        this.earnedScore = earnedScore;
        this.examDate = examDate;
    }
}
