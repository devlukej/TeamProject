package com.example.tp.service;

import com.example.tp.domain.entity.TestHistory;
import com.example.tp.domain.repository.TestHistoryRepository;
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

    public List<TestHistory> getTestHistoryByUserId(Long userId) {
        return testHistoryRepository.findByUserId(userId);
    }
}

