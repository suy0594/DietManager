package com.practice.dietmanager.controller;

import com.practice.dietmanager.service.ChatService;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping("/api/v1/chat-gpt")
public class testController {

    private final ChatService chatService;
    private final ChatgptService chatgptService;

    @PostMapping
    public String test(@RequestBody String question) {
        String str = chatService.getChatResponse(question);
        log.info("출력된 답변 : {}", str);
        return str;
    }

}
