package com.example.tp.domain.repository;


import com.example.tp.domain.entity.BoardEntity;
import com.example.tp.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {


    @Modifying
    @Query("UPDATE BoardEntity b SET b.boardHits = b.boardHits + 1 WHERE b.id = :id")
    void updateHits(@Param("id") Long id);


    Page<BoardDTO> findByCategory(String category, Pageable pageable);

}














