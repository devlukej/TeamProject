package com.example.tp.Controller;


import com.example.tp.domain.entity.QuestionEntity;
import com.example.tp.domain.entity.UserEntity;
import com.example.tp.domain.repository.QuestionRepository;
import com.example.tp.domain.repository.QcommentRepository;
import com.example.tp.domain.repository.UserRepository;
import com.example.tp.dto.QuestionDTO;
import com.example.tp.dto.QcommentDTO;
import com.example.tp.service.QuestionService;
import com.example.tp.service.QcommentService;
import com.example.tp.service.MemberUser;
import com.example.tp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QcommentService qcommentService;
    private final UserService userService;

    private final QcommentRepository qcommentRepository;

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    @GetMapping("/public/question/save")
    public String saveForm(@AuthenticationPrincipal MemberUser user,Model model) {

        if (user == null) {

            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "board/question/save";
    }

    @PostMapping("/public/question/save")
    public String save(@AuthenticationPrincipal MemberUser user, @ModelAttribute QuestionDTO questionDTO, Model model) throws IOException {
        if (user != null) {
            // 현재 로그인한 사용자 정보를 이용하여 작성자 필드 설정
            questionDTO.setQuestionWriter(user.getNickname());
            userService.increaseUserTier(user.getUserEntity(), 3);
        }

        questionService.save(questionDTO, user);

        model.addAttribute("user", user);

        return "redirect:/public/question";
    }


    @GetMapping("/public/question")
    public String findAll(@AuthenticationPrincipal MemberUser user,Model model, @PageableDefault(page = 1) Pageable pageable, @RequestParam(value = "category", required = false) String category) {

        Page<QuestionDTO> questionList = questionService.paging(pageable);;


        // 각 게시글에 대한 댓글 수를 계산하고 추가
        for (QuestionDTO question : questionList) {
            Long questionId = question.getId();
            QuestionEntity questionEntity = questionService.getQuestionEntityById(questionId);
            Long qcommentCount = qcommentRepository.countByQuestionEntity(questionEntity);
            question.setCommentCount(qcommentCount);
        }

        for (QuestionDTO question : questionList) {
            Long questionId = question.getId();
            QuestionEntity questionEntity = questionService.getQuestionEntityById(questionId);
            int recommendCount = questionEntity.getRecommendCount(); // 게시물의 추천 수
            question.setRecommendCount(recommendCount); // 추천 수 설정
        }


        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = Math.min(startPage + blockLimit - 1, questionList.getTotalPages());

        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3
        // 현재 사용자가 7페이지
        // 7 8 9
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수 8개


        model.addAttribute("user", user);
        model.addAttribute("questionList", questionList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/question/question";
    }


    @GetMapping("/public/question/{id}")
    public String findById(@AuthenticationPrincipal MemberUser user,
            @PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         */

        if (user == null) {

            return "redirect:/login";
        }

        questionService.updateHits(id);
        QuestionDTO questionDTO = questionService.findById(id);
        /* 댓글 목록 가져오기 */
        List<QcommentDTO> qcommentDTOList = qcommentService.findAll(id);

        model.addAttribute("user", user);
        model.addAttribute("qcommentList", qcommentDTOList);
        model.addAttribute("question", questionDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "board/question/detail";
    }

    @GetMapping("/public/question/update/{id}")
    public String updateForm(@AuthenticationPrincipal MemberUser user,@PathVariable Long id, Model model) {

        if (user == null) {

            return "redirect:/login";
        }

        QuestionDTO questionDTO = questionService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("questionUpdate", questionDTO);
        return "board/question/update";
    }

    @PostMapping("/public/question/update")
    public String update(@ModelAttribute QuestionDTO questionDTO, Model model) {
        QuestionDTO question = questionService.update(questionDTO);
        model.addAttribute("question", question);
//        return "redirect:detail";

        return "redirect:/public/question/" + questionDTO.getId();
    }

    @GetMapping("/public/question/delete/{id}")
    public String delete(@PathVariable Long id) {
        questionService.delete(id);

        return "redirect:/public/question";
    }

    @PostMapping("/public/question/recommend/{id}")
    public ResponseEntity<Map<String, String>> recommend(@PathVariable("id") Long id, @AuthenticationPrincipal MemberUser user) {
        Map<String, String> response = new HashMap<>();
        if (user == null) {
            response.put("message", "로그인 후 추천할 수 있습니다.");
            response.put("recommended", "false");
        } else {
            QuestionEntity question = questionService.getQuestionEntityById(id);
            UserEntity currentUser = user.getUserEntity();
            if (currentUser.getRecommendedQuestionIds().contains(id)) {
                response.put("message", "이미 추천한 게시물입니다.");
                response.put("recommended", "true");
            } else {
                question.setRecommendCount(question.getRecommendCount() + 1);
                currentUser.getRecommendedQuestionIds().add(id);
                questionRepository.save(question);
                userRepository.save(currentUser);
                response.put("message", "게시물을 성공적으로 추천했습니다.");
                response.put("recommended", "true");
            }
        }
        return ResponseEntity.ok(response);
    }







}










