package com.example.tp.service;


import com.example.tp.domain.entity.BoardEntity;
import com.example.tp.domain.repository.BoardRepository;
import com.example.tp.domain.repository.CommentRepository;
import com.example.tp.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    public void save(BoardDTO boardDTO, MemberUser user) throws IOException {

        boardDTO.setBoardWriter(user.getNickname());

        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            // 댓글 수 계산 및 설정
            Long commentCount = commentRepository.countByBoardEntity(boardEntity);
            boardDTO.setCommentCount(commentCount);
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }

    public class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String entityName, Long entityId) {
            super("Entity " + entityName + " with ID " + entityId + " not found");
        }
    }


    public BoardEntity getBoardEntityById(Long boardId) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardId);
        if (optionalBoardEntity.isPresent()) {
            return optionalBoardEntity.get();
        } else {
            String entityName = "BoardEntity"; // 엔티티 이름
            throw new EntityNotFoundException(entityName, boardId);
        }
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        // 목록: id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardContents(), board.getBoardHits(), board.getCreatedTime(), board.getCategory()));
        return boardDTOS;
    }

    public Page<BoardDTO> pagingByCategory(String category, Pageable pageable) {
        return boardRepository.findByCategory(category, pageable);
    }
}














