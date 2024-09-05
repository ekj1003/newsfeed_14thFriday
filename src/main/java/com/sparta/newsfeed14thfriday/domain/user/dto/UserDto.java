package com.sparta.newsfeed14thfriday.domain.user.dto;

import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserDto {
    private final String email;

    public UserDto(String email) {
        this.email = email;
    }
}
