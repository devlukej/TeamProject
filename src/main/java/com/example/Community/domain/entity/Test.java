package com.example.Community.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "test")
public class Test {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long num;
    @Column(length = 20)
    private String title;
    @Column(length = 20)
    private String view1;
    @Column(length = 20)
    public String view2;
    @Column(length = 20)
    public String view3;
    @Column(length = 20)
    public String view4;
    @Column(length = 20)
    public String view5;
    @Column(length = 20)
    public String answer;
    @Column(length = 20)
    private String interpretation;
    @Column(length = 20)
    private String year;
    @Column(length = 20)
    private String subject;
    @Column(length = 20)
    private String name;
    @Column(length = 20)
    private String filePath;

    public boolean isCorrect(String selectedAnswer) {
        return answer.equals(selectedAnswer);
    }

    @Builder
    public Test(Long num, String title, String answer, String view1, String view2
            , String view3, String view4, String view5, String interpretation, String year, String subject, String name, String filePath) {
        this.answer = answer;
        this.num = num;
        this.title = title;
        this.view1 = view1;
        this.view2 = view2;
        this.view3 = view3;
        this.view4 = view4;
        this.view5 = view5;
        this.interpretation = interpretation;
        this.year = year;
        this.subject = subject;
        this.name = name;
        this.filePath = filePath;
    }

}
