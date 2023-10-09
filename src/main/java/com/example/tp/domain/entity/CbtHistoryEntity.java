package com.example.tp.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "cbt_history")
public class CbtHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exam_date")
    private Date examDate;

    @Column(name = "total_score")
    private int totalScore;

    @Builder
    public CbtHistoryEntity(UserEntity user, Date examDate, int totalScore) {
        this.user = user;
        this.examDate = examDate;
        this.totalScore = totalScore;
    }
}

