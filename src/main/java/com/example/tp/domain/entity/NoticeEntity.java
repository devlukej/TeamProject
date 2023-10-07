package com.example.tp.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "notice")
public class NoticeEntity extends TimeEntity {

    @Id
    @Column(length = 20, nullable = false)
    private Integer num;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String content;

    @Column(length = 20, nullable = false)
    private Integer inquery;

    @Column(length = 20, nullable = false)
    private String id;

    @Builder
    public NoticeEntity(Integer num, String title, String content, Integer inquery, String id) {
        this.num = num;
        this.title = title;
        this.content = content;
        this.inquery = inquery;
        this.id = id;
    }
}
