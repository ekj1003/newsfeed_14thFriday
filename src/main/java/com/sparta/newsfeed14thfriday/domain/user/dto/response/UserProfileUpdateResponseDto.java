package com.sparta.newsfeed14thfriday.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserProfileUpdateResponseDto {
    private final String userName;

    public UserProfileUpdateResponseDto(String userName) {
        this.userName = userName;
    }
}
