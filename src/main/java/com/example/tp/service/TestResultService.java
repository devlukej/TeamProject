package com.example.tp.service;

import com.example.tp.domain.entity.TestResult;
import com.example.tp.domain.repository.TestResultRepository;
import com.example.tp.dto.TestResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestResultService {

    private final TestResultRepository testResultRepository;

    @Autowired
    public TestResultService(TestResultRepository testResultRepository) {
        this.testResultRepository = testResultRepository;
    }

    @Transactional
    public void saveAllTestResults(List<TestResult> testResults) {
        testResultRepository.saveAll(testResults);
    }


    public List<TestResultDto> getUserResults(String userId) {
        // 사용자 이름(username)을 기준으로 해당 사용자의 시험 결과를 조회
        List<TestResult> userResults = testResultRepository.findByUser_Id(userId);

        // TestResult 엔터티를 TestResultDto로 변환하는 작업
        List<TestResultDto> userResultDtos = userResults.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return userResultDtos;
    }

    private TestResultDto convertToDto(TestResult testResult) {
        TestResultDto testResultDto = new TestResultDto();

        testResultDto.setId(testResult.getId()); // TestResultDto에 있는 필드에 값을 설정합니다.
        testResultDto.setSelectedAnswer(testResult.getSelectedAnswer());
        testResultDto.setAnswer(testResult.getAnswer());
        testResultDto.setCorrect(testResult.isCorrect());


        // TestResultDto와 TestResult 엔터티 간에 필드를 추가로 매핑하려면 이어서 설정합니다.
        testResultDto.setTest(testResult.getTest());

        return testResultDto;
    }


}

