package com.sparta.newsfeed14thfriday.domain.user.dto;

import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import lombok.Getter;

@Getter
public class SignupResponseDto {

    private String email;
    private String username;

    public SignupResponseDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
