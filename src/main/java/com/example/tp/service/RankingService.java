package com.example.tp.service;

import com.example.tp.domain.entity.UserEntity;
import com.example.tp.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class RankingService {
    private final UserRepository userRepository;

    @Autowired
    public RankingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getRankingSortedByTier() {
        List<UserEntity> rankedUsers = userRepository.findUsersByTier();
        rankedUsers.sort(Comparator.comparingInt(UserEntity::getTier).reversed());
        return rankedUsers;
    }
}
