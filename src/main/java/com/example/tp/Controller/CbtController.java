package com.example.tp.Controller;

import com.example.tp.domain.entity.CbtHistoryEntity;
import com.example.tp.domain.entity.CbtResultEntity;
import com.example.tp.domain.entity.Test;
import com.example.tp.dto.TestDto;
import com.example.tp.service.CbtResultService;
import com.example.tp.service.MemberUser;
import com.example.tp.service.TestServiceImpl;
import com.example.tp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/private")
public class CbtController {

    private final CbtResultService cbtResultService;
    private final TestServiceImpl testService;
    private final UserService userService;

    @Autowired
    public CbtController(CbtResultService cbtResultService, TestServiceImpl testService, UserService userService) {
        this.cbtResultService = cbtResultService;
        this.testService = testService;
        this.userService = userService;
    }

    @GetMapping("/cbt-result")
    public String showCbtResult(@AuthenticationPrincipal MemberUser user, Model model, @RequestParam("id") String id) {
        // 이곳에서 시험 결과 데이터를 가져와서 모델에 추가하거나 필요한 처리를 수행할 수 있습니다.

        // 예시 데이터를 모델에 추가
        model.addAttribute("totalScore", 80); // 총점 예시
        model.addAttribute("isCorrect", true); // 정답 여부 예시
        model.addAttribute("user", user);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse("2023-10-09");
            System.out.println("파싱 성공: " + date);
        } catch (ParseException e) {
            System.out.println("파싱 실패: " + e.getMessage());
            e.printStackTrace();
        }


        // 예시 시험 히스토리 데이터를 모델에 추가
        List<CbtHistoryEntity> cbtHistories = new ArrayList<>();
//        cbtHistories.add(new CbtHistoryEntity(userService.getUserById(id), date, 90)); // 시험 번호 1의 히스토리
//        cbtHistories.add(new CbtHistoryEntity(userService.getUserById(id), date, 75)); // 시험 번호 2의 히스토리
        // 모델에 시험 히스토리 리스트 추가
        model.addAttribute("cbtHistories", cbtHistories);


        // cbt-result 페이지로 이동
        return "board/cbt/cbt-result"; // cbt-result HTML 페이지의 경로
    }

    @PostMapping("/submit-cbt")
    public String submitCbt(Principal principal, RedirectAttributes redirectAttributes,
                            @RequestParam("name") String name,
                            @RequestParam("year") String year,
                            @RequestParam("subject") String subject,
                            @RequestParam("selectedAnswers") List<Integer> selectedAnswers) {

        // 사용자 ID 가져오기
        String userId = principal.getName();

        // 여기에서 시험 문제 채점 로직을 구현하고 총점 계산 등의 작업을 수행합니다.
        // 각 문제를 채점하고 총점을 계산합니다.

        // 시험 문제 조회
        List<TestDto> tests = testService.getQuestionsByCategories(name, year, subject);

        // 총점 초기화
        int totalScore = 0;

        // 채점 로직: 선택한 답과 정답을 비교하여 점수 부여
        for (int i = 0; i < tests.size(); i++) {
            int selectedAnswer = selectedAnswers.get(i);
            TestDto test = tests.get(i);
            int correctAnswer = Integer.parseInt(test.getAnswer()); // 시험 문제의 정답을 가져옵니다.

            // 정답 여부 확인
            boolean isCorrectAnswer = (selectedAnswer == correctAnswer);

            // 정답일 때 10점 부여
            int earnedScore = isCorrectAnswer ? 10 : 0;

            // 총점에 더함
            totalScore += earnedScore;

            // 시험 결과 저장
            CbtResultEntity cbtResult = CbtResultEntity.builder()
                    .user(userService.getUserById(userId))
                    .test(Test.builder().num(test.getNum()).build())
                    .selectedAnswer(selectedAnswer)
                    .correct(isCorrectAnswer)
                    .earnedScore(earnedScore)
                    .build();

            cbtResultService.saveCbtResult(cbtResult);
        }

        // 시험 히스토리 저장
        CbtHistoryEntity cbtHistory = CbtHistoryEntity.builder()
                .user(userService.getUserById(userId))
                .totalScore(totalScore)
                .build();
        cbtResultService.saveCbtHistory(cbtHistory);

        // 총점과 정답 여부를 리다이렉트 시킬 때 함께 전달하기 위해 RedirectAttributes 사용
        redirectAttributes.addFlashAttribute("totalScore", totalScore);

        return "redirect:/private/cbt-result";
    }
}
