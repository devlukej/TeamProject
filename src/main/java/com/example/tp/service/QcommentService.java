package com.example.tp.service;


import com.example.tp.domain.entity.QuestionEntity;
import com.example.tp.domain.entity.QcommentEntity;
import com.example.tp.domain.entity.UserEntity;
import com.example.tp.domain.repository.QuestionRepository;
import com.example.tp.domain.repository.QcommentRepository;
import com.example.tp.domain.repository.QcommentRepository;
import com.example.tp.dto.QcommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QcommentService {
    private final QcommentRepository qcommentRepository;
    private final QuestionRepository questionRepository;

    public Long save(QcommentDTO qcommentDTO, UserEntity commentWriter) {
        /* 부모엔티티(QuestionEntity) 조회 */
        Optional<QuestionEntity> optionalQuestionEntity = questionRepository.findById(qcommentDTO.getQuestionId());
        if (optionalQuestionEntity.isPresent()) {
            QuestionEntity questionEntity = optionalQuestionEntity.get();
            QcommentEntity qcommentEntity = QcommentEntity.toSaveEntity(qcommentDTO, questionEntity, commentWriter);
            return qcommentRepository.save(qcommentEntity).getId();
        } else {
            return null;
        }
    }

    public List<QcommentDTO> findAll(Long questionId) {
        QuestionEntity questionEntity = questionRepository.findById(questionId).get();
        List<QcommentEntity> qcommentEntityList = qcommentRepository.findAllByQuestionEntityOrderByIdAsc(questionEntity);
        /* EntityList -> DTOList */
        List<QcommentDTO> qcommentDTOList = new ArrayList<>();
        for (QcommentEntity qcommentEntity: qcommentEntityList) {
            QcommentDTO qcommentDTO = QcommentDTO.toQcommentDTO(qcommentEntity, questionId);
            qcommentDTOList.add(qcommentDTO);
        }
        return qcommentDTOList;
    }

}
