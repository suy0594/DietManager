package com.practice.dietmanager.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class MenuController {

    @GetMapping("/register_menu")
    public String register_menuMenu() {
        log.info("**********식단 입력 페이지로 이동**********");
        return "register_menu"; // register_menu.html을 반환
    }

    @GetMapping("/diary")
    public String diaryMenu() {
        log.info("**********기록 페이지로 이동**********");
        return "diary";
    }

    @GetMapping("/settings")
    public String settingMenu() {
        log.info("**********설정 페이지로 이동**********");
        return "settings";
    }


}
