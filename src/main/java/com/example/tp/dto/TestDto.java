package com.example.tp.dto;

import com.example.tp.domain.entity.Test;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestDto {

    private Long num;
    private String title;
    private String view1;
    private String view2;
    private String view3;
    private String view4;
    private String view5;
    private String answer;
    private String interpretation;
    private String year;
    private String subject;
    private String name;
    private String filePath;

    public Test toEntity() {
        return Test.builder()
                .num(num)
                .title(title)
                .view1(view1)
                .view2(view2)
                .view3(view3)
                .view4(view4)
                .view5(view5)
                .answer(answer)
                .interpretation(interpretation)
                .year(year)
                .subject(subject)
                .name(name)
                .filePath(filePath)
                .build();
    }

    @Builder
    public TestDto(Long num, String title, String answer, String view1, String view2
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
