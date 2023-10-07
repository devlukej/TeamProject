package com.example.tp.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "question")
public class QuestionEntity extends TimeEntity {

    @Id
    @Column(length = 20, nullable = false)
    private Integer num;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String content;

    @Column(length = 20)
    private Integer inquery;

    @Column(length = 20, nullable = false)
    private Integer recommend;

    @Column(length = 20)
    private String id;

    @Column(length = 20)
    private String tier;



    @Builder
    public QuestionEntity(Integer num, String title, String content, Integer inquery, Integer recommend, String id, String tier) {
        this.num = num;
        this.title = title;
        this.content = content;
        this.inquery = inquery;
        this.recommend = recommend;
        this.id = id;
        this.tier = tier;
    }
}