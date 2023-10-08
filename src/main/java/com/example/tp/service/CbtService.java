package com.example.tp.service;
import com.example.tp.domain.entity.CbtEntity;
import com.example.tp.domain.repository.CbtRepository;
import com.example.tp.dto.CbtDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CbtService {
    private CbtRepository cbtRepository;

    @Transactional
    public List<CbtDto> getCbtlist() {
        List<CbtEntity> cbtEntities = cbtRepository.findAll();
        List<CbtDto> cbtDtoList = new ArrayList<>();

        for (CbtEntity cbtEntity : cbtEntities) {
            CbtDto cbtDTO = CbtDto.builder()
                    .count(cbtEntity.getCount())
                    .choice(cbtEntity.getChoice())
                    .answer(cbtEntity.getAnswer())
                    .id(cbtEntity.getId())
                    .num(cbtEntity.getNum())
                    .name(cbtEntity.getName())
                    .type(cbtEntity.getType())
                    .build();

            cbtDtoList.add(cbtDTO);
        }

        return cbtDtoList;
    }
}
