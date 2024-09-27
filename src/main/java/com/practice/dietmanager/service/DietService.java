package com.practice.dietmanager.service;

import com.practice.dietmanager.domain.entity.Diet;
import com.practice.dietmanager.domain.entity.User;
import com.practice.dietmanager.repository.DietRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Log4j2
public class DietService {

    private final DietRepository dietRepository;
    private final UserService userService;
    private final ChatService chatService;

    public void createDiet(Diet diet, String username, LocalDate today) {
        User user = userService.registerUser(username);
        diet.setUser(user); // 생성한 사용자 설정
        diet.setDate(today);

        // ChatGPT에 질문하기 위한 프롬프트 생성
        String prompt = String.format("다음 음식을 분석해줘 name: %s, weight: %s and provide the " +
                        "carbohydrate, protein, and fat content. " +
                        "답변 형식은, 'carbohydrate: ?g, protein: ?g, fat: ?g'으로 해줘 '.'도 붙이면 안돼",
                diet.getFoodName(), diet.getWeight());

        // ChatGPT로부터 응답 받기
        String chatResponse = chatService.getChatResponse(prompt);
        log.info("답변: {}", chatResponse);

        String response = extractNutritionalValues(chatResponse);
        log.info("추출해낸 문자열 : {}", response);
        // 응답을 분석하여 탄수화물, 단백질, 지방 값을 추출하는 로직
        String[] responseParts = response.split(","); // 응답을 쉼표로 나누기
        int carbohydrate = 0, protein = 0, fat = 0; // 영양소 초기화
        for (String part : responseParts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim().toLowerCase(); // 키 소문자로 변환
                String value = keyValue[1].trim().replace("g", ""); // 단위 'g' 제거

                // 추출한 영양소 값을 저장
                switch (key) {
                    case "carbohydrate":
                        carbohydrate = Integer.parseInt(value);
                        break;
                    case "protein":
                        protein = Integer.parseInt(value);
                        break;
                    case "fat":
                        fat = Integer.parseInt(value);
                        break;
                }
            }
        }

        diet.setCarbohydrate(carbohydrate);
        diet.setProtein(protein);
        diet.setFat(fat);
        dietRepository.save(diet); // 새로운 식단 저장

    }
    public String extractNutritionalValues(String response) {
        // 정규 표현식 패턴 생성
        String regex = "carbohydrate:\\s*(\\d+)g,\\s*protein:\\s*(\\d+)g,\\s*fat:\\s*(\\d+)g";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
            int carbohydrate = Integer.parseInt(matcher.group(1)); // 탄수화물
            int protein = Integer.parseInt(matcher.group(2));     // 단백질
            int fat = Integer.parseInt(matcher.group(3));         // 지방

            // 결과 문자열 생성
            return String.format("carbohydrate: %dg, protein: %dg, fat: %dg", carbohydrate, protein, fat);
        } else {
            return "Nutritional values not found in the response.";
        }
    }

    // 일단 1번 유저로 고정해놓고 리턴
    public List<Diet> registerDiet(LocalDate date, String username) {
        User user = userService.registerUser(username);
        List<Diet> diets = dietRepository.findByUserAndDate(user, date);
        if (diets.isEmpty()) {
            Diet newDiet = new Diet();
            newDiet.setCarbohydrate(0);
            newDiet.setProtein(0);
            newDiet.setFat(0);
            diets.add(newDiet);
            log.info("새로 추가한 영양소 : {}", newDiet);
        }
        log.info("return 하는 리스트 출력 : {}", diets);
        return diets;
    }

}
