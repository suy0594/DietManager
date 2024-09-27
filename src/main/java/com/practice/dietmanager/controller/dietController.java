package com.practice.dietmanager.controller;

import com.practice.dietmanager.domain.entity.Diet;
import com.practice.dietmanager.domain.entity.User;
import com.practice.dietmanager.service.DietService;
import com.practice.dietmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
public class dietController {

    private final DietService dietService;
    private final UserService userService;

    @GetMapping("/")
    public String homeDiet() {
        return "redirect:/analysis";
    }

    @GetMapping("/analysis")
    public String getTodayDiet(Model model) {
        // 현재 인증된 유저 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // 오늘 날짜의 다이어트 정보를 가져옴
        LocalDate today = LocalDate.now();
        List<Diet> todayDiet = dietService.registerDiet(today, username);

        Diet diet = todayDiet.stream().reduce(
                new Diet(), (total, n) -> {
                    total.setCarbohydrate(total.getCarbohydrate() + n.getCarbohydrate());
                    total.setProtein(total.getProtein() + n.getProtein());
                    total.setFat(total.getFat() + n.getFat());
                    return total;
                }
        );

        log.info("총 영양소 정보 : {}", diet);

        // 모델에 다이어트 데이터 추가
        model.addAttribute("diet", diet);

        return "index";  // 보여줄 뷰 이름 (HTML 템플릿 파일명)
    }

    @PostMapping("/create")
    public String createDiet(@ModelAttribute Diet diet, @AuthenticationPrincipal UserDetails user, Model model) {
        log.info("**********create post 요청 받음**********");
        log.info(diet.toString());
        // 다이어트 정보를 서비스에 저장
        dietService.createDiet(diet, user.getUsername(), LocalDate.now()); // 실제 서비스 호출

        // 성공 메시지를 모델에 추가
        model.addAttribute("message", "다이어트 정보가 성공적으로 등록되었습니다!");

        log.info("**********create 성공 home으로 redirect**********");
        // 성공 후 리다이렉트 또는 다른 뷰 반환

        return "redirect:/analysis"; // 등록 후 이동할 URL
    }

    @PostMapping("/register")
    public String registerDiet(@RequestParam("selected_date") LocalDate selectedDate,
                               @AuthenticationPrincipal UserDetails user, Model model) {
        log.info("**********register post 요청**********");
        log.info("Selected date: {}", selectedDate);

        List<Diet> diets = dietService.registerDiet(selectedDate, user.getUsername()).stream().toList();
        log.info("**********불러오기 성공**********");
        int totalCalories = diets.stream().mapToInt(Diet::getCalories).sum();

        model.addAttribute("diet", diets);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("totalCalories", totalCalories);

        log.info("**********model 등록 성공**********");

        return "diary";
    }



}
