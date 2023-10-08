package com.example.tp.dto;


import com.example.tp.domain.entity.CbtEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CbtDto {
    private Integer count;
    private Integer choice;
    private Integer answer;
    private String id;
    private String num;
    public String name;
    private String type;
    private LocalDate createDate;


    public CbtEntity toEntity() {
        return CbtEntity.builder()
                .id(id)
                .answer(answer)
                .type(type)
                .count(count)
                .choice(choice)
                .num(num)
                .name(name)
                .build();
    }

    @Builder
    public CbtDto(Integer count,Integer choice, Integer answer, String id, String num, String name, String type,LocalDate createDate) {
        this.id = id;
        this.answer = answer;
        this.type = type;
        this.count = count;
        this.choice = choice;
        this.num = num;
        this.name = name;
        this.createDate = createDate;
    }
}