package com.example.Community.domain.repository;


import com.example.Community.domain.entity.QcommentEntity;
import com.example.Community.domain.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QcommentRepository extends JpaRepository<QcommentEntity, Long> {
    // select * from comment_table where question_id=? order by id desc;
    List<QcommentEntity> findAllByQuestionEntityOrderByIdAsc(QuestionEntity questionEntity);

    Long countByQuestionEntity(QuestionEntity questionEntity);
}
