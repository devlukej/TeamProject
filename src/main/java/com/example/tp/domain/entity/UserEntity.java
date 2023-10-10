package com.example.tp.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity extends TimeEntity {

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

    @Column(length = 2)
    private String state;

    @ColumnDefault("0")
    private String tier;

    @Column(columnDefinition = "TEXT")
    private String filePath;


    @Builder
    public UserEntity(String id, String pw, String name, String phone, String tier, String gender, String state, String birthday, String filePath, Date date) {
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