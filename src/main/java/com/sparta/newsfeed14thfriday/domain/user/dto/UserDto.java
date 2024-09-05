package com.sparta.newsfeed14thfriday.domain.user.dto;

import lombok.Getter;

@Getter
public class UserDto {
    private final String email;

    public UserDto(String email) {
        this.email = email;
    }
}
