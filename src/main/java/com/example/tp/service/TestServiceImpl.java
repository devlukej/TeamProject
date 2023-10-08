package com.example.tp.service;

import com.example.tp.domain.entity.Test;
import com.example.tp.domain.repository.TestRepository;
import com.example.tp.dto.TestDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl {
    private final TestRepository testRepository;

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<TestDto> getQuestionsByCategories(String name, String year, String type) {

        logger.info("getQuestionsByCategories 호출: name={}, year={}, type={}", name, year, type);

        List<Test> tests = testRepository.findByNameAndYearAndType(name, year, type);

        logger.info("검색 결과: tests={}", tests);

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
        testDto.setType(test.getType());
        return testDto;
    }
}