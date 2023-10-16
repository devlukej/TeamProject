package com.example.tp.Controller;

import com.example.tp.domain.entity.UserEntity;
import com.example.tp.dto.CommentDTO;
import com.example.tp.service.CommentService;
import com.example.tp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.example.tp.service.MemberUser;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    @PostMapping("/public/board/comment/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO, @AuthenticationPrincipal MemberUser user) {
        if (user != null) {
            UserEntity commentWriter = user.getUserEntity(); // 현재 로그인한 사용자의 아이디를 작성자로 설정
            Long saveResult = commentService.save(commentDTO, commentWriter);
            if (saveResult != null) {
                List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());

                if (user != null) {
                    // 현재 로그인한 사용자의 tier를 3씩 증가시킵니다.
                    userService.increaseUserTier(user.getUserEntity(), 3);
                }

                return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("댓글 작성을 위해서 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
    }


}
