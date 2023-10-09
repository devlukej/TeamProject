package com.example.tp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CbtResultDto {

    private Long id;
    private int score;
    private String testName;
    // 필요한 필드 추가
    // 게터/세터 추가
}

