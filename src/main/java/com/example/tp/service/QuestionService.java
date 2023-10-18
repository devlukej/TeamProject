package com.example.tp.service;


import com.example.tp.domain.entity.BoardEntity;
import com.example.tp.domain.entity.QuestionEntity;
import com.example.tp.domain.repository.BoardRepository;
import com.example.tp.domain.repository.QcommentRepository;
import com.example.tp.domain.repository.QuestionRepository;
import com.example.tp.dto.BoardDTO;
import com.example.tp.dto.QuestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    private final QcommentRepository qcommentRepository;

    public void save(QuestionDTO questionDTO, MemberUser user) throws IOException {

        questionDTO.setQuestionWriter(user.getNickname());

        QuestionEntity questionEntity = QuestionEntity.toSaveEntity(questionDTO);
        questionRepository.save(questionEntity);
    }

    public List<QuestionDTO> findAll() {
        List<QuestionEntity> questionEntityList = questionRepository.findAll();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (QuestionEntity questionEntity : questionEntityList) {
            QuestionDTO questionDTO = QuestionDTO.toQuestionDTO(questionEntity);

            // 댓글 수 계산 및 설정
            Long commentCount = qcommentRepository.countByQuestionEntity(questionEntity);
            questionDTO.setCommentCount(commentCount);
            questionDTOList.add(questionDTO);

            int recommendCount = questionEntity.getRecommendCount();
            questionDTO.setRecommendCount(recommendCount);

        }
        return questionDTOList;
    }

    public class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String entityName, Long entityId) {
            super("Entity " + entityName + " with ID " + entityId + " not found");
        }
    }


    public QuestionEntity getQuestionEntityById(Long questionId) {
        Optional<QuestionEntity> optionalQuestionEntity = questionRepository.findById(questionId);
        if (optionalQuestionEntity.isPresent()) {
            return optionalQuestionEntity.get();
        } else {
            String entityName = "QuestionEntity"; // 엔티티 이름
            throw new EntityNotFoundException(entityName, questionId);
        }
    }

    @Transactional
    public void updateHits(Long id) {
        questionRepository.updateHits(id);
    }

    @Transactional
    public QuestionDTO findById(Long id) {
        Optional<QuestionEntity> optionalQuestionEntity = questionRepository.findById(id);
        if (optionalQuestionEntity.isPresent()) {
            QuestionEntity questionEntity = optionalQuestionEntity.get();
            QuestionDTO questionDTO = QuestionDTO.toQuestionDTO(questionEntity);

            int recommendCount = questionEntity.getRecommendCount();
            questionDTO.setRecommendCount(recommendCount);
            return questionDTO;
        } else {
            return null;
        }
    }

    public QuestionDTO update(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = QuestionEntity.toUpdateEntity(questionDTO);
        questionRepository.save(questionEntity);
        return findById(questionDTO.getId());
    }

    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    public Page<QuestionDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        Page<QuestionEntity> questionEntities =
                questionRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        // 목록: id, writer, title, hits, createdTime
        Page<QuestionDTO> questionDTOS = questionEntities.map(question -> new QuestionDTO(question.getId(), question.getQuestionWriter(), question.getQuestionTitle(), question.getQuestionContents(), question.getQuestionHits(), question.getCreatedTime(), question.getCategory(), question.getRecommendCount()));
        return questionDTOS;
    }
}














