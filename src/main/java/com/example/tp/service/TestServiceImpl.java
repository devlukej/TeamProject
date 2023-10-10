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

    public Test getTestByTestId(Long testId) {
        // 여기에 특정 테스트를 testId로 검색하여 반환하는 로직을 구현해야 합니다.
        // 예를 들어, Spring Data JPA를 사용한다면 다음과 같이 구현할 수 있습니다.
        // TestRepository는 Test 엔티티를 다루는 Spring Data JPA 리포지토리 인터페이스입니다.
        return testRepository.findById(testId).orElse(null);
    }


    // 문제 번호로 시험 문제 가져오기
    public Test getTestByQuestionNumber(Long questionNumber) {
        return testRepository.findById(questionNumber)
                .orElseThrow(() -> new RuntimeException("시험 문제를 찾을 수 없습니다."));
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