package com.example.tp.mapper;

import com.example.tp.domain.entity.Test;
import com.example.tp.dto.TestDto;

import java.util.List;
import java.util.stream.Collectors;

public class TestMapper {

    public static TestDto toDto(Test entity) {
        TestDto dto = new TestDto();
        dto.setNum(entity.getNum());
        dto.setTitle(entity.getTitle());
        dto.setView1(entity.getView1());
        dto.setView2(entity.getView2());
        dto.setView3(entity.getView3());
        dto.setView4(entity.getView4());
        dto.setView5(entity.getView5());
        dto.setAnswer(entity.getAnswer());
        dto.setInterpretation(entity.getInterpretation());
        dto.setYear(entity.getYear());
        dto.setType(entity.getType());
        dto.setName(entity.getName());
        return dto;
    }

    public static List<TestDto> toDtoList(List<Test> entities) {
        return entities.stream()
                .map(TestMapper::toDto)
                .collect(Collectors.toList());
    }
}
