package com.practice.dietmanager.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "UserSetting")
public class UserSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int dailyCalorieGoal;
    private String language;
    private String theme;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;



}
