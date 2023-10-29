package com.example.Community.service;

import com.example.Community.domain.entity.TestHistory;
import com.example.Community.domain.repository.TestHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TestHistoryService {
    private final TestHistoryRepository testHistoryRepository;

    @Autowired
    public TestHistoryService(TestHistoryRepository testHistoryRepository) {
        this.testHistoryRepository = testHistoryRepository;
    }
    @Transactional
    public TestHistory saveTestHistory(TestHistory testHistory) {
        return testHistoryRepository.save(testHistory);
    }

    @Transactional
    public List<TestHistory> getTestHistoryByUserIdAndDate(String userId) {
        return testHistoryRepository.findByUserIdOrderByCreatedTime(userId);
    }

    public List<TestHistory> getTestHistoryByUserId(Long userId) {
        return testHistoryRepository.findByUserId(userId);
    }
}

