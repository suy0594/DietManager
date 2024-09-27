package com.practice.dietmanager.repository;

import com.practice.dietmanager.domain.entity.Diet;
import com.practice.dietmanager.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DietRepository extends JpaRepository<Diet, Long> {
    Diet findByUser(User user);
    List<Diet> findByUserAndDate(User user, LocalDate date);
}
