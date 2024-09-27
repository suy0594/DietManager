package com.practice.dietmanager.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserJoinRequestDTO {

    private String username;
    private String email;
    private String password;

    @Builder
    public UserJoinRequestDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }


}
