package com.example.tp.dto;


import com.example.tp.domain.entity.QcommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class QcommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long QuestionId;
    private LocalDateTime commentCreatedTime;

    public static QcommentDTO toQcommentDTO(QcommentEntity qcommentEntity, Long boardId) {
        QcommentDTO qcommentDTO = new QcommentDTO();
        qcommentDTO.setId(qcommentEntity.getId());
        qcommentDTO.setCommentWriter(qcommentEntity.getCommentWriter());
        qcommentDTO.setCommentContents(qcommentEntity.getCommentContents());
        qcommentDTO.setCommentCreatedTime(qcommentEntity.getCreatedTime());
        qcommentDTO.setQuestionId(boardId);
        return qcommentDTO;
    }
}
