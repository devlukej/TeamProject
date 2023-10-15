package com.example.tp.domain.repository;

import com.example.tp.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByNickname(String Nickname);
    List<UserEntity> findAll();

    List<UserEntity> findByState(String state);

    @Query("SELECT u FROM UserEntity u ORDER BY u.tier DESC")
    List<UserEntity> findUsersByTier();

//    List<UserEntity> findByNameContaining(String nameKeyword);
//
//    List<UserEntity> findByYearContaining(String yearKeyword);
//
//    List<UserEntity> findBySessionContaining(String sessionKeyword);
//
//    List<UserEntity> findByPositionContaining(String positionKeyword);

}