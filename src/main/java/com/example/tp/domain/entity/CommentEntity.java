package com.example.tp.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity extends TimeEntity {

    @Id
    @Column(length = 20, nullable = false)
    private Integer no;

    @Column(length = 100, nullable = false)
    private String content;

    @Column(length = 20, nullable = false)
    private Integer recommend;

    @Column(length = 20, nullable = false)
    private String id;

    @Column(length = 20)
    private String tier;

    @Column(length = 20)
    private Integer num;



    @Builder
    public CommentEntity(Integer no, String content, Integer recommend, String id, String tier, Integer num) {
        this.no = no;
        this.content = content;
        this.recommend = recommend;
        this.id = id;
        this.tier = tier;
        this.num = num;
    }
}