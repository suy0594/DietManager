package com.practice.dietmanager.controller;

import com.practice.dietmanager.domain.entity.UserSetting;
import com.practice.dietmanager.service.UserService;
import com.practice.dietmanager.service.UserSettingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
@Log4j2
public class SettingController {

    private final UserSettingService userSettingService;
    private final LocaleResolver localeResolver;
    private final UserService userService;

    /*
    @GetMapping("/settings")
    public String showSettings(Model model) {
        // 현재 사용자의 설정을 가져와 모델에 추가
        UserSetting userSetting = userSettingService.getUserSetting(getCurrentUserId());
        model.addAttribute("userSetting", userSetting);
        return "settings";
    }
    */
    @PostMapping("/save_settings")
    public String saveSettings(@ModelAttribute UserSetting userSetting,
                               @AuthenticationPrincipal UserDetails user,
                               HttpServletRequest request, HttpServletResponse response,
                               Model model) {
        log.info(userSetting.toString());
        userSettingService.saveUserSetting(userSetting, user.getUsername());

        // 언어 설정 변경
        Locale newLocale = Locale.forLanguageTag(userSetting.getLanguage());
        localeResolver.setLocale(request, response, newLocale);
        log.info("언어설정 + ", userSetting.getLanguage());
        model.addAttribute("user", userService.registerUser(user.getUsername()));
        return "redirect:/analysis";
    }

    private Long getCurrentUserId() {
        // 현재 로그인한 사용자의 ID를 반환하는 로직
        // 예: return SecurityContextHolder.getContext().getAuthentication().getPrincipal().getId();
        return 1L; // 임시로 1을 반환
    }
}