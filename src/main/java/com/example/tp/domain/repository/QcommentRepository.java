package com.example.tp.domain.repository;


import com.example.tp.domain.entity.QcommentEntity;
import com.example.tp.domain.entity.QuestionEntity;
import com.example.tp.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QcommentRepository extends JpaRepository<QcommentEntity, Long> {
    // select * from comment_table where question_id=? order by id desc;
    List<QcommentEntity> findAllByQuestionEntityOrderByIdAsc(QuestionEntity questionEntity);

    Long countByQuestionEntity(QuestionEntity questionEntity);
}
