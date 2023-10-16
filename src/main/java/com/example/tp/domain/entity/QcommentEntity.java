package com.example.tp.domain.entity;


import com.example.tp.dto.CommentDTO;
import com.example.tp.dto.QcommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Qcomment_table")
public class QcommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    /* Question:Comment = 1:N */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private QuestionEntity questionEntity;


    public static QcommentEntity toSaveEntity(QcommentDTO qcommentDTO, QuestionEntity questionEntity, UserEntity commentWriter) {
        QcommentEntity qcommentEntity = new QcommentEntity();
        qcommentEntity.setCommentContents(qcommentDTO.getCommentContents());
        qcommentEntity.setQuestionEntity(questionEntity);
        qcommentEntity.setCommentWriter(commentWriter);
        return qcommentEntity;
    }
    public void setCommentWriter(UserEntity commentWriter) {
        this.commentWriter = commentWriter.getNickname(); // UserEntity의 nickname 필드 사용
    }


}
