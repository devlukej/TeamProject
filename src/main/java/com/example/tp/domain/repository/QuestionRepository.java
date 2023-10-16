package com.example.tp.domain.repository;


import com.example.tp.domain.entity.BoardEntity;
import com.example.tp.domain.entity.QuestionEntity;
import com.example.tp.dto.BoardDTO;
import com.example.tp.dto.QuestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {


    @Modifying
    @Query("UPDATE QuestionEntity b SET b.questionHits = b.questionHits + 1 WHERE b.id = :id")
    void updateHits(@Param("id") Long id);


    Page<QuestionDTO> findByCategory(String category, Pageable pageable);

}














