package com.example.tp.dto;


import com.example.tp.domain.entity.QuestionEntity;
import lombok.*;

import java.time.LocalDateTime;

// DTO(Data Transfer Object), VO, Bean,         Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class QuestionDTO {
    private Long id;
    private String questionWriter;
    private String questionTitle;
    private String questionContents;
    private int questionHits;
    private LocalDateTime questionCreatedTime;
    private LocalDateTime questionUpdatedTime;
    private String category;

    private Long commentCount;

    private int recommendCount;


    public QuestionDTO(Long id, String questionWriter, String questionTitle, String questionContents, int questionHits, LocalDateTime questionCreatedTime, String category, int recommendCount) {
        this.id = id;
        this.questionWriter = questionWriter;
        this.questionTitle = questionTitle;
        this.questionContents = questionContents;
        this.questionHits = questionHits;
        this.questionCreatedTime = questionCreatedTime;
        this.category = category;
        this.recommendCount = recommendCount;
    }

    public static QuestionDTO toQuestionDTO(QuestionEntity questionEntity) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(questionEntity.getId());
        questionDTO.setQuestionWriter(questionEntity.getQuestionWriter());
        questionDTO.setQuestionTitle(questionEntity.getQuestionTitle());
        questionDTO.setQuestionContents(questionEntity.getQuestionContents());
        questionDTO.setQuestionHits(questionEntity.getQuestionHits());
        questionDTO.setQuestionCreatedTime(questionEntity.getCreatedTime());
        questionDTO.setQuestionUpdatedTime(questionEntity.getUpdatedTime());
        questionDTO.setCategory(questionEntity.getCategory());
        questionDTO.setRecommendCount(questionEntity.getRecommendCount());

        return questionDTO;
    }
}
