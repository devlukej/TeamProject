package com.example.tp.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "cbt")
public class CbtEntity extends TimeEntity {

    @Id
    @Column(length = 20, nullable = false)
    private Integer count;

    @Column(length = 100, nullable = false)
    private Integer choice;

    @Column(length = 300, nullable = false)
    private Integer answer;

    @Column(length = 20)
    private String id;

    @Column(length = 20, nullable = false)
    private String num;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String type;




    @Builder
    public CbtEntity(Integer count, Integer choice, Integer answer, String id, String num, String name, String type ) {
        this.count = count;
        this.choice = choice;
        this.answer = answer;
        this.id = id;
        this.num = num;
        this.name = name;
        this.type = type;
    }

}
