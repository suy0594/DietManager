package com.practice.dietmanager.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "Diet")
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String foodName;

    private int calories;
    private int mealTime;

    private int carbohydrate;
    private int protein;
    private int fat;

    private String weight;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}