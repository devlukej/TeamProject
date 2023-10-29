package com.example.Community.domain.repository;


import com.example.Community.domain.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {


    @Modifying
    @Query("UPDATE BoardEntity b SET b.boardHits = b.boardHits + 1 WHERE b.id = :id")
    void updateHits(@Param("id") Long id);

    Page<BoardEntity> findByBoardTitleContaining(String boardTitle, Pageable pageable);

    Page<BoardEntity> findByBoardContentsContaining(String boardContents, Pageable pageable);

    Page<BoardEntity> findByBoardWriterContaining(String boardWriter, Pageable pageable);

}














