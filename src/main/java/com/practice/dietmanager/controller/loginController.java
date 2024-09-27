package com.practice.dietmanager.controller;

import com.practice.dietmanager.domain.entity.UserJoinRequestDTO;
import com.practice.dietmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class loginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginMenu() {
        log.info("**********로그인 페이지 요청**********");
        return "login";
    }


    @GetMapping("/login/register")
    public String registerMenu() {
        log.info("**********회원가입 페이지 요청**********");
        return "register";
    }

    @PostMapping("/login/register")
    public String registerPostMenu(@ModelAttribute UserJoinRequestDTO requestDTO) {
        log.info("**********회원가입 요청**********");
        userService.join(requestDTO);
        return "login";
    }

}
