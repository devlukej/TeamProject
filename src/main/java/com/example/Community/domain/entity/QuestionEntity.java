package com.example.Community.domain.entity;


import com.example.Community.dto.QuestionDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// DB의 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "question_table")
public class QuestionEntity extends BaseEntity {
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String questionWriter;

    @Column
    private String questionTitle;

    @Column(length = 500)
    private String questionContents;

    @Column
    private int questionHits;

    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QcommentEntity> qcommentEntityList = new ArrayList<>();

    @Column
    private String category;

    @Column(columnDefinition = "integer default 0")
    private int recommendCount;


    public static QuestionEntity toSaveEntity(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setQuestionWriter(questionDTO.getQuestionWriter());
        questionEntity.setQuestionTitle(questionDTO.getQuestionTitle());
        questionEntity.setQuestionContents(questionDTO.getQuestionContents());
        questionEntity.setQuestionHits(0);
        return questionEntity;
    }

    public static QuestionEntity toUpdateEntity(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(questionDTO.getId());
        questionEntity.setQuestionWriter(questionDTO.getQuestionWriter());
        questionEntity.setQuestionTitle(questionDTO.getQuestionTitle());
        questionEntity.setQuestionContents(questionDTO.getQuestionContents());
        questionEntity.setQuestionHits(questionDTO.getQuestionHits());
        return questionEntity;
    }

    public int getRecommendCount() {
        return this.recommendCount;
    }


}











