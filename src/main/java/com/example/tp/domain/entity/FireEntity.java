package com.example.tp.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "fire")
public class FireEntity extends TimeEntity {

    @Id
    @Column(length = 20, nullable = false)
    private String id;

    @Column(length = 100, nullable = false)
    private String pw;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20)
    private String phone;

    @Column(length = 20, nullable = false)
    private String gender;

    @Column(length = 20)
    private String birthday;

    @Column(length = 20)
    private String state;

    @Column(length = 20)
    private String tier;

    @Column(columnDefinition = "TEXT")
    private String filePath;

    @Builder
    public FireEntity(String id, String pw, String name, String phone, String tier, String gender, String state, String birthday, String filePath) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.tier = tier;
        this.gender = gender;
        this.birthday = birthday;
        this.state = state;
        this.filePath = filePath;

    }
}
