package com.example.tp.Controller;

import com.example.tp.service.TestServiceImpl;
import com.example.tp.dto.TestDto;
import com.example.tp.domain.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    private final TestRepository testRepository;
    private final TestServiceImpl testService;

    @Autowired
    public TestController(TestRepository testRepository, TestServiceImpl testService) {
        this.testRepository = testRepository;
        this.testService = testService;
    }

    @GetMapping("/private/pre-cbt")
    public String categorySelectionPage() {

        logger.info("categorySelectionPage 호출");

        return "board/TestCategorySelection"; // 카테고리 선택 HTML 페이지
    }

    @PostMapping("/private/cbt")
    public String resultPage(
            @RequestParam("name") String name,
            @RequestParam("year") String year,
            @RequestParam("type") String type,
            Model model) {

        logger.info("resultPage 호출: name={}, year={}, type={}", name, year, type);

        List<TestDto> tests = testService.getQuestionsByCategories(name, year, type);

        model.addAttribute("tests", tests);
        return "board/TestResult"; // 결과 표시 HTML 페이지
    }
}
