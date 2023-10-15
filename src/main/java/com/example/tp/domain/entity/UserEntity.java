package com.example.tp.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.*;


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

    @Column(length = 20, nullable = false, unique = true)
    private String nickname;

    @Column(length = 20)
    private String phone;

    @Column(length = 20, nullable = false)
    private String gender;

    @Column(length = 20)
    private String birthday;

    @Column(length = 2)
    private String state;

    @ColumnDefault("0")
    private Integer tier;

    @Column(columnDefinition = "TEXT")
    private String filePath;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "recommended_boards", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "board_id")
    private Set<Long> recommendedBoardIds = new HashSet<>();


    @Builder
    public UserEntity(String id, String pw, String name, String nickname, String phone, Integer tier, String gender, String state, String birthday, String filePath, Date date) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.tier = tier;
        this.gender = gender;
        this.birthday = birthday;
        this.state = state;
        this.filePath = filePath;

    }


}