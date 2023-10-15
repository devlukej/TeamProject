package com.example.tp.Controller;


import com.example.tp.dto.NoticeDTO;
import com.example.tp.dto.CommentDTO;
import com.example.tp.service.NoticeService;
import com.example.tp.service.CommentService;
import com.example.tp.service.MemberUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/public/notice/save")
    public String saveForm(@AuthenticationPrincipal MemberUser user,Model model) {

        if (user == null) {

            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "board/notice/save";
    }

    @PostMapping("/public/notice/save")
    public String save(@AuthenticationPrincipal MemberUser user,@ModelAttribute NoticeDTO noticeDTO,Model model) throws IOException {

        if (user != null) {
            // 현재 로그인한 사용자 정보를 이용하여 작성자 필드 설정
            noticeDTO.setNoticeWriter(user.getNickname());
        }

        noticeService.save(noticeDTO, user);

        model.addAttribute("user", user);

       return "redirect:/public/notice";

    }

    @GetMapping("/public/notice")
    public String findAll(@AuthenticationPrincipal MemberUser user,Model model, @PageableDefault(page = 1) Pageable pageable) {

        Page<NoticeDTO> noticeList = noticeService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = Math.min(startPage + blockLimit - 1, noticeList.getTotalPages());

        if (user == null) {

            return "redirect:/login";
        }

        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3
        // 현재 사용자가 7페이지
        // 7 8 9
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수 8개
        model.addAttribute("user", user);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<NoticeDTO> noticeDTOList = noticeService.findAll();

        return "board/notice/notice";
    }

    @GetMapping("/public/notice/{id}")
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

        noticeService.updateHits(id);
        NoticeDTO noticeDTO = noticeService.findById(id);

        model.addAttribute("user", user);
        model.addAttribute("notice", noticeDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "board/notice/detail";
    }

    @GetMapping("/public/notice/update/{id}")
    public String updateForm(@AuthenticationPrincipal MemberUser user,@PathVariable Long id, Model model) {

        if (user == null) {

            return "redirect:/login";
        }

        NoticeDTO noticeDTO = noticeService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("noticeUpdate", noticeDTO);
        return "board/notice/update";
    }

    @PostMapping("/public/notice/update")
    public String update(@ModelAttribute NoticeDTO noticeDTO, Model model) {
        NoticeDTO notice = noticeService.update(noticeDTO);
        model.addAttribute("notice", notice);
//        return "redirect:detail";

        return "redirect:/public/notice/" + noticeDTO.getId();
    }

    @GetMapping("/public/notice/delete/{id}")
    public String delete(@PathVariable Long id) {
        noticeService.delete(id);

        return "redirect:/public/notice";
    }

}










