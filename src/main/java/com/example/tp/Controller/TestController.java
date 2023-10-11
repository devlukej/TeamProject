package com.example.tp.Controller;

import com.example.tp.domain.entity.TestHistory;
import com.example.tp.domain.entity.TestResult;
import com.example.tp.domain.entity.Test;
import com.example.tp.domain.entity.UserEntity;
import com.example.tp.domain.repository.TestRepository;
import com.example.tp.dto.TestResultDto;
import com.example.tp.service.MemberUser;
import com.example.tp.service.TestHistoryService;
import com.example.tp.service.TestResultService;
import com.example.tp.service.TestServiceImpl;
import com.example.tp.dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    private final TestServiceImpl testService;
    private final TestRepository testRepository;
    private final TestResultService testResultService;

    private final TestHistoryService testHistoryService;

    @Autowired
    public TestController(TestServiceImpl testService, TestRepository testRepository,TestResultService testResultService,TestHistoryService testHistoryService) {
        this.testService = testService;
        this.testRepository = testRepository;
        this.testResultService = testResultService;
        this.testHistoryService = testHistoryService;
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
    @Transactional
    @PostMapping("/private/submit-cbt")
    public String submitCbt(@AuthenticationPrincipal MemberUser user, @RequestParam Map<String, String> requestParams, Model model, HttpServletRequest request) {
        if (user == null) {
            return "redirect:/login";
        }

        List<TestResult> testResults = new ArrayList<>();
        int totalScore = 0;

        // 사용자의 선택 항목을 수집하는 로직 추가
        for (String paramName : requestParams.keySet()) {
            if (paramName.startsWith("selectedAnswers[")) {
                String selectedAnswer = requestParams.get(paramName);

                // paramName에서 인덱스를 추출
                int index = Integer.parseInt(paramName.replace("selectedAnswers[", "").replace("]", ""));

                // testId 관련 파라미터를 가져오고 null 체크를 수행
                String testIdParamName = "testId[" + index + "]";
                if (requestParams.containsKey(testIdParamName)) {
                    Long testId = Long.parseLong(requestParams.get(testIdParamName));

                    // Test 엔터티에서 해당 테스트 가져오기
                    Test test = testService.getTestByTestId(testId);

                    if (test != null) {
                        boolean isCorrect = test.isCorrect(selectedAnswer);

                        if (isCorrect) {
                            totalScore += 2.5; // 정답인 경우 총점 증가
                        }

                        TestResult testResult = new TestResult();
                        testResult.setUser(user.getUserEntity());
                        testResult.setTest(test);
                        testResult.setSelectedAnswer(selectedAnswer);
                        testResult.setCorrect(isCorrect);
                        testResult.setAnswer(test.getAnswer());

                        testResults.add(testResult);

                    } else {
                        // 해당 테스트를 찾을 수 없는 경우 예외 처리 또는 처리할 내용을 추가
                    }
                } else {
                    // testIdParamName이 없는 경우 처리할 내용을 추가
                }
            }
        }

        // testResults에 결과 저장
        testResultService.saveAllTestResults(testResults);

        // TestHistory 엔터티 생성 및 저장
        if (!testResults.isEmpty()) {
            TestHistory testHistory = new TestHistory();
            testHistory.setUserId(Long.parseLong(user.getUserEntity().getId()));
            testHistory.setTotalScore(totalScore);

            testHistoryService.saveTestHistory(testHistory);
        }

        // HttpSession 객체를 통해 totalScore를 세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute("totalScore", totalScore);

        model.addAttribute("testResults", testResults);

        model.addAttribute("totalScore", totalScore); // totalScore는 총점 변수 이름

        model.addAttribute("user", user);
        return "redirect:/private/cbt-result";
    }





    @GetMapping("/private/cbt-result")
    public String showCbtResultPage(@AuthenticationPrincipal MemberUser user, Model model, HttpServletRequest request) {

        if (user == null) {
            return "redirect:/login";
        }

        // TestResult 엔티티에서 사용자의 결과 조회
        List<TestResultDto> userResults = testResultService.getUserResults(user.getUsername());

        HttpSession session = request.getSession();
        int totalScore = (int) session.getAttribute("totalScore");

        model.addAttribute("user", user);
        model.addAttribute("totalScore", totalScore);
        model.addAttribute("userResults", userResults);

        return "board/cbt/cbt-result"; // 시험 결과를 표시하는 HTML 페이지
    }

}