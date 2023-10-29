package com.example.Community.domain.repository;


import com.example.Community.domain.entity.QuestionEntity;
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


    Page<QuestionEntity> findByQuestionTitleContaining(String questionTitle, Pageable pageable);

    Page<QuestionEntity> findByQuestionContentsContaining(String questionContents, Pageable pageable);

    Page<QuestionEntity> findByQuestionWriterContaining(String questionWriter, Pageable pageable);

}














