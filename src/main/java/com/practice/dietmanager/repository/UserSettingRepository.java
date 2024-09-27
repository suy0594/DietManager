package com.practice.dietmanager.repository;

import com.practice.dietmanager.domain.entity.User;
import com.practice.dietmanager.domain.entity.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, Long> {
    UserSetting findByUser(User user);
}