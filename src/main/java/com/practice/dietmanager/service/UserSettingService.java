package com.practice.dietmanager.service;

import com.practice.dietmanager.domain.entity.User;
import com.practice.dietmanager.domain.entity.UserSetting;
import com.practice.dietmanager.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSettingService {

    private final UserService userService;
    private final UserSettingRepository userSettingRepository;

    public UserSetting saveUserSetting(UserSetting userSetting, String username) {
        User user = userService.registerUser(username);
        userSetting.setUser(user);
        return userSettingRepository.save(userSetting);
    }

    public UserSetting getUserSetting(String username) {
        User user = userService.registerUser(username);
        return userSettingRepository.findByUser(user);
    }
}
