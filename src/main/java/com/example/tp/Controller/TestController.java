package com.example.tp.Controller;

import com.example.tp.domain.entity.TestResult;
import com.example.tp.domain.entity.Test;
import com.example.tp.domain.entity.UserEntity;
import com.example.tp.domain.repository.TestRepository;
import com.example.tp.dto.TestResultDto;
import com.example.tp.service.MemberUser;
import com.example.tp.service.TestResultService;
import com.example.tp.service.TestServiceImpl;
import com.example.tp.dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    private final TestServiceImpl testService;
    private final TestRepository testRepository;
    private final TestResultService testResultService;

    @Autowired
    public TestController(TestServiceImpl testService, TestRepository testRepository,TestResultService testResultService) {
        this.testService = testService;
        this.testRepository = testRepository;
        this.testResultService = testResultService;
    }

    @GetMapping("/private/pre-cbt")
    public String categorySelectionPage(@AuthenticationPrincipal MemberUser user, Model model) {

        if (user == null) {

            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "board/cbt/pre-cbt"; // 카테고리 선택 HTML 페이지
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

        return "board/cbt/cbt";
    }
    @PostMapping("/private/submit-cbt")
    public String submitCbt(@AuthenticationPrincipal MemberUser user,
                            @RequestParam Map<String, String> requestParams, Model model) {

        if (user == null) {
            return "redirect:/login";
        }

        // 선택한 답안 처리
        for (String paramName : requestParams.keySet()) {
            // 만약 paramName이 "testId"인 경우에만 처리
            if ("testId".equals(paramName)) {
                String testIdValue = requestParams.get(paramName);

                // "testId" 값을 Long으로 파싱
                try {
                    Long testId = Long.parseLong(testIdValue);

                    // Test 엔터티에서 해당 테스트 가져오기
                    Test test = testService.getTestByTestId(testId);

                    if (test != null) {
                        // 정답 여부 확인 및 결과 저장
                        String selectedAnswer = requestParams.get("selectedAnswer");
                        boolean isCorrect = test.isCorrect(selectedAnswer);

                        TestResult testResult = new TestResult();
                        testResult.setUser(user.getUserEntity());
                        testResult.setTest(test);
                        testResult.setSelectedAnswer(selectedAnswer);
                        testResult.setCorrect(isCorrect);

                        testResultService.saveTestResult(testResult);
                    } else {
                        // 해당 테스트를 찾을 수 없는 경우 예외 처리
                        // 처리할 내용 추가
                    }
                } catch (NumberFormatException e) {
                    // "testId" 값을 숫자로 변환할 수 없는 경우 예외 처리
                    // 처리할 내용 추가
                }
            }
        }

        model.addAttribute("user", user);
        // 여기에서 원하는 처리 및 리다이렉션 수행
        return "redirect:/private/cbt-result";
    }



    @GetMapping("/private/cbt-result")
    public String showCbtResultPage(@AuthenticationPrincipal MemberUser user, Model model) {

        if (user == null) {
            return "redirect:/login";
        }

        // TestResult 엔티티에서 사용자의 결과 조회
        List<TestResultDto> userResults = testResultService.getUserResults(user.getUsername());

        model.addAttribute("user", user);
        model.addAttribute("userResults", userResults);

        return "board/cbt/cbt-result"; // 시험 결과를 표시하는 HTML 페이지
    }

}