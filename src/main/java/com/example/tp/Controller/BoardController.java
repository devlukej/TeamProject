package com.example.tp.Controller;


import com.example.tp.domain.entity.BoardEntity;
import com.example.tp.domain.entity.UserEntity;
import com.example.tp.domain.repository.BoardRepository;
import com.example.tp.domain.repository.CommentRepository;
import com.example.tp.domain.repository.UserRepository;
import com.example.tp.dto.BoardDTO;
import com.example.tp.dto.CommentDTO;
import com.example.tp.service.BoardService;
import com.example.tp.service.CommentService;
import com.example.tp.service.MemberUser;
import com.example.tp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final UserService userService;

    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    @GetMapping("/public/board/save")
    public String saveForm(@AuthenticationPrincipal MemberUser user,Model model) {

        if (user == null) {

            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "board/board/save";
    }

    @PostMapping("/public/board/save")
    public String save(@AuthenticationPrincipal MemberUser user, @ModelAttribute BoardDTO boardDTO, Model model) throws IOException {
        if (user != null) {
            // 현재 로그인한 사용자 정보를 이용하여 작성자 필드 설정
            boardDTO.setBoardWriter(user.getNickname());
            userService.increaseUserTier(user.getUserEntity(), 3);
        }

        boardService.save(boardDTO, user);

        model.addAttribute("user", user);

        return "redirect:/public/board";
    }

    @GetMapping("/public/board")
    public String findAll(@AuthenticationPrincipal MemberUser user,Model model, @PageableDefault(page = 1) Pageable pageable, @RequestParam(value = "nameKeyword", required = false) String nameKeyword) {

        Page<BoardDTO> boardList = boardService.paging(pageable);


        // 각 게시글에 대한 댓글 수를 계산하고 추가
        for (BoardDTO board : boardList) {
            Long boardId = board.getId();
            BoardEntity boardEntity = boardService.getBoardEntityById(boardId);
            Long commentCount = commentRepository.countByBoardEntity(boardEntity);
            board.setCommentCount(commentCount);
        }

        for (BoardDTO board : boardList) {
            Long boardId = board.getId();
            BoardEntity boardEntity = boardService.getBoardEntityById(boardId);
            int recommendCount = boardEntity.getRecommendCount(); // 게시물의 추천 수
            board.setRecommendCount(recommendCount); // 추천 수 설정
        }


        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = Math.min(startPage + blockLimit - 1, boardList.getTotalPages());

        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3
        // 현재 사용자가 7페이지
        // 7 8 9
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수 8개


        model.addAttribute("user", user);
        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/board/board";
    }


    @GetMapping("/public/board/{id}")
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

        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        /* 댓글 목록 가져오기 */
        List<CommentDTO> commentDTOList = commentService.findAll(id);

        model.addAttribute("user", user);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "board/board/detail";
    }

    @GetMapping("/public/board/update/{id}")
    public String updateForm(@AuthenticationPrincipal MemberUser user,@PathVariable Long id, Model model) {

        if (user == null) {

            return "redirect:/login";
        }

        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("boardUpdate", boardDTO);
        return "board/board/update";
    }

    @PostMapping("/public/board/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
//        return "redirect:detail";

        return "redirect:/public/board/" + boardDTO.getId();
    }

    @GetMapping("/public/board/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);

        return "redirect:/public/board";
    }

    @PostMapping("/public/board/recommend/{id}")
    public ResponseEntity<Map<String, String>> recommend(@PathVariable("id") Long id, @AuthenticationPrincipal MemberUser user) {
        Map<String, String> response = new HashMap<>();
        if (user == null) {
            response.put("message", "로그인 후 추천할 수 있습니다.");
            response.put("recommended", "false");
        } else {
            BoardEntity board = boardService.getBoardEntityById(id);
            UserEntity currentUser = user.getUserEntity();
            if (currentUser.getRecommendedBoardIds().contains(id)) {
                response.put("message", "이미 추천한 게시물입니다.");
                response.put("recommended", "true");
            } else {
                board.setRecommendCount(board.getRecommendCount() + 1);
                currentUser.getRecommendedBoardIds().add(id);
                boardRepository.save(board);
                userRepository.save(currentUser);
                response.put("message", "게시물을 성공적으로 추천했습니다.");
                response.put("recommended", "true");
            }
        }
        return ResponseEntity.ok(response);
    }







}










