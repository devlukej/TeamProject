package com.example.Community.service;

import com.example.Community.domain.entity.UserEntity;
import com.example.Community.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

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
