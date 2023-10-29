package com.example.Community.domain.entity;


import com.example.Community.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    /* Board:Comment = 1:N */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;


    public static CommentEntity toSaveEntity(CommentDTO commentDTO, BoardEntity boardEntity,UserEntity commentWriter) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setCommentWriter(commentWriter);
        return commentEntity;
    }
    public void setCommentWriter(UserEntity commentWriter) {
        this.commentWriter = commentWriter.getNickname(); // UserEntity의 nickname 필드 사용
    }


}
