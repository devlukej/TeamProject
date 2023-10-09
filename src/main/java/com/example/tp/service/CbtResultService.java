package com.example.tp.service;

import com.example.tp.domain.entity.CbtHistoryEntity;
import com.example.tp.domain.entity.CbtResultEntity;
import com.example.tp.domain.repository.CbtHistoryRepository;
import com.example.tp.domain.repository.CbtResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CbtResultService {

    private final CbtResultRepository cbtResultRepository;
    private final CbtHistoryRepository cbtHistoryRepository;

    @Autowired
    public CbtResultService(CbtResultRepository cbtResultRepository, CbtHistoryRepository cbtHistoryRepository) {
        this.cbtResultRepository = cbtResultRepository;
        this.cbtHistoryRepository = cbtHistoryRepository;
    }

    public void saveCbtResult(CbtResultEntity cbtResult) {
        cbtResultRepository.save(cbtResult);
    }

    public void saveCbtHistory(CbtHistoryEntity cbtHistory) {
        cbtHistoryRepository.save(cbtHistory);
    }
}
