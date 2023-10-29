package com.example.Community.domain.repository;


import com.example.Community.domain.entity.BoardEntity;
import com.example.Community.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdAsc(BoardEntity boardEntity);

    Long countByBoardEntity(BoardEntity boardEntity);
}
