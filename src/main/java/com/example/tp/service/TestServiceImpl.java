package com.example.tp.service;

import com.example.tp.domain.entity.Test;
import com.example.tp.domain.repository.TestRepository;
import com.example.tp.dto.TestDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl {
    private final TestRepository testRepository;

    @Autowired
    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<TestDto> getQuestionsByCategories(String name, String year, String subject) {

        List<Test> tests = testRepository.findByNameAndYearAndSubject(name, year, subject);

        return tests.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    private TestDto convertToDto(Test test) {
        TestDto testDto = new TestDto();
        testDto.setNum(test.getNum());
        testDto.setTitle(test.getTitle());
        testDto.setView1(test.getView1());
        testDto.setView2(test.getView2());
        testDto.setView3(test.getView3());
        testDto.setView4(test.getView4());
        testDto.setView5(test.getView5());
        testDto.setAnswer(test.getAnswer());
        testDto.setInterpretation(test.getInterpretation());
        testDto.setFilePath(test.getFilePath());
        testDto.setName(test.getName());
        testDto.setYear(test.getYear());
        testDto.setSubject(test.getSubject());
        return testDto;
    }
}