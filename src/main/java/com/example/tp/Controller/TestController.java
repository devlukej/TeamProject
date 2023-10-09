package com.example.tp.Controller;

import com.example.tp.service.MemberUser;
import com.example.tp.service.TestServiceImpl;
import com.example.tp.dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    private final TestServiceImpl testService;

    @Autowired
    public TestController(TestServiceImpl testService) {
        this.testService = testService;
    }

    @GetMapping("/private/pre-cbt")
    public String categorySelectionPage(@AuthenticationPrincipal MemberUser user, Model model) {

        if (user == null) {

            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "board/pre-cbt"; // 카테고리 선택 HTML 페이지
    }

    // 시험 문제를 표시하는 페이지
    @PostMapping("/private/cbt")
    public String showCbt(@AuthenticationPrincipal MemberUser user,
                          @RequestParam("name") String name,
                          @RequestParam("year") String year,
                          @RequestParam("subject") String subject, Model model) {

        if (user == null) {
            return "redirect:/login";
        }

        List<TestDto> tests = testService.getQuestionsByCategories(name, year, subject);

        model.addAttribute("user", user);
        model.addAttribute("tests", tests);

        return "board/cbt";
    }


}