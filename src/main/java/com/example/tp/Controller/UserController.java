package com.example.tp.Controller;

import com.example.tp.domain.entity.TestHistory;
import com.example.tp.domain.entity.UserEntity;
import com.example.tp.dto.NoticeDTO;
import com.example.tp.dto.UserDto;
import com.example.tp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private NoticeService noticeService;

    private RankingService rankingService;

    private TestHistoryService testHistoryService;


    @GetMapping("/")
    public String list(@AuthenticationPrincipal MemberUser user, Model model) {

        if (user != null) {

            model.addAttribute("user", user);
        }

        List<NoticeDTO> latestNotices = noticeService.findLatestNotices(5);

        List<UserEntity> ranking = rankingService.getRankingSortedByTier();


        model.addAttribute("ranking", ranking);
        model.addAttribute("noticeList", latestNotices);

        return "board/main";
    }



    // 회원가입 페이지
    @GetMapping("/signup")
    public String dispSignup() {

        return "user/signup";
    }


    // 회원가입 처리
    @PostMapping("/signup")
    public String execSignup(UserDto userDto) {


        userService.joinUser(userDto);

        return "redirect:/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String dispLogin() {

        return "user/login";
    }

    // 접근 거부 페이지
    @GetMapping("/denied")
    public String dispDenied() {

        return "user/denied";
    }

    // 내 정보 페이지
    @GetMapping("/public/ranking")
    public String dispRanking(@AuthenticationPrincipal MemberUser user, Model model) {

        List<UserEntity> ranking = rankingService.getRankingSortedByTier();

        model.addAttribute("ranking", ranking);
        model.addAttribute("user", user);
        return "user/ranking";
    }

    // 내 정보 페이지
    @GetMapping("/private/myinfo")
    public String dispMyInfo(@AuthenticationPrincipal MemberUser user, Model model) {

        if (user == null) {

            return "redirect:/login";
        }

        List<UserDto> userDtoList = userService.getList();

        model.addAttribute("userList", userDtoList);
        model.addAttribute("user", user);
        return "user/myinfo";
    }

    @PostMapping("/private/myinfo")
    public String dispMyInfo(@AuthenticationPrincipal MemberUser user, Model model , UserDto userDto) {
        userService.savePost(userDto);
        model.addAttribute("user", user);

        return "redirect:/myinfo";
    }

    @PostMapping("/checkDuplicateId")
    public ResponseEntity<Map<String, String>> checkDuplicateId(@RequestParam("id") String id) {
        Map<String, String> response = new HashMap<>();

        if (userService.isIdUnique(id)) {
            response.put("message", "available");
        } else {
            response.put("message", "duplicate");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkDuplicateNickname")
    public ResponseEntity<Map<String, String>> checkDuplicateNickname(@RequestParam("nickname") String nickname) {
        Map<String, String> response = new HashMap<>();

        if (userService.isNicknameUnique(nickname)) {
            response.put("message", "available");
        } else {
            response.put("message", "duplicate");
        }

        return ResponseEntity.ok(response);
    }

//    //이름검색
//    @GetMapping("/admin/userList/nameKeyword")
//    public String searchUserName(@RequestParam(value = "nameKeyword") String nameKeyword, Model model, @AuthenticationPrincipal MemberUser user) {
//
//        model.addAttribute("user", user);
//
//        if (Objects.equals(nameKeyword, "")) {
//
//            return "redirect:/admin/userList";
//
//        } else {
//
//            List<UserDto> userDtoList = userService.searchUserName(nameKeyword);
//            model.addAttribute("userList", userDtoList);
//        }
//
//        return "/admin/userList";
//    }
//
//    //기수검색
//    @GetMapping("/admin/userList/yearKeyword")
//    public String searchUserYear(@RequestParam(value = "yearKeyword") String yearKeyword, Model model, @AuthenticationPrincipal MemberUser user) {
//
//        model.addAttribute("user", user);
//        if (Objects.equals(yearKeyword, "")) {
//
//            return "redirect:/admin/userList";
//
//        } else {
//            List<UserDto> userDtoList = userService.searchUserYear(yearKeyword);
//            model.addAttribute("userList", userDtoList);
//        }
//        return "/admin/userList";
//    }
//
//    //세션검색
//    @GetMapping("/admin/userList/sessionKeyword")
//    public String searchUserSession(@RequestParam(value = "sessionKeyword") String sessionKeyword, Model model, @AuthenticationPrincipal MemberUser user) {
//
//        model.addAttribute("user", user);
//        if (Objects.equals(sessionKeyword, "")) {
//
//            return "redirect:/admin/userList";
//
//        } else {
//            List<UserDto> userDtoList = userService.searchUserSession(sessionKeyword);
//            model.addAttribute("userList", userDtoList);
//        }
//        return "/admin/userList";
//    }
//
//    //직급검색
//    @GetMapping("/admin/userList/positionKeyword")
//    public String searchUserPosition(@RequestParam(value = "positionKeyword") String positionKeyword, Model model, @AuthenticationPrincipal MemberUser user) {
//
//        model.addAttribute("user", user);
//        if (Objects.equals(positionKeyword, "")){
//
//            return "redirect:/admin/userList";
//
//        } else {
//            List<UserDto> userDtoList = userService.searchUserPosition(positionKeyword);
//            model.addAttribute("userList", userDtoList);
//        }
//        return "/admin/userList";
//    }

}
