package com.example.tp.Controller;

import com.example.tp.dto.TestDto;
import com.example.tp.service.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TestController {
    private final TestServiceImpl testService;

    @Autowired
    public TestController(TestServiceImpl testService) {
        this.testService = testService;
    }

    @GetMapping("/private/pre-cbt")
    public String categorySelectionPage() {
        return "board/TestCategorySelection"; // 카테고리 선택 HTML 페이지
    }

    @PostMapping("/private/cbt")
    public String resultPage(
            @RequestParam("name") String name,
            @RequestParam("year") String year,
            @RequestParam("type") String type,
            Model model) {
        List<TestDto> tests = testService.getQuestionsByCategories(name, year, type);
        model.addAttribute("tests", tests);
        return "board/TestResult"; // 결과 표시 HTML 페이지
    }


}
