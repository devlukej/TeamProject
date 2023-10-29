package com.example.Community.Controller;

import com.example.Community.domain.entity.UserEntity;
import com.example.Community.dto.QcommentDTO;
import com.example.Community.service.QcommentService;
import com.example.Community.service.MemberUser;
import com.example.Community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QcommentController {
    private final QcommentService qcommentService;
    private final UserService userService;
    @PostMapping("/public/question/comment/save")
    public ResponseEntity save(@ModelAttribute QcommentDTO qcommentDTO, @AuthenticationPrincipal MemberUser user) {
        if (user != null) {
            UserEntity commentWriter = user.getUserEntity(); // 현재 로그인한 사용자의 아이디를 작성자로 설정
            Long saveResult = qcommentService.save(qcommentDTO, commentWriter);
            if (saveResult != null) {
                List<QcommentDTO> qcommentDTOList = qcommentService.findAll(qcommentDTO.getQuestionId());

                if (user != null) {
                    // 현재 로그인한 사용자의 tier를 3씩 증가시킵니다.
                    userService.increaseUserTier(user.getUserEntity(), 3);
                }

                return new ResponseEntity<>(qcommentDTOList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("댓글 작성을 위해서 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
    }


}
