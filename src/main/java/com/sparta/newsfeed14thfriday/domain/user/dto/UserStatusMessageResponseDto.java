package com.sparta.newsfeed14thfriday.domain.user.dto;

import lombok.Getter;

@Getter
public class UserStatusMessageResponseDto {
    private String updatedStatusMessage;

    public UserStatusMessageResponseDto(String message) {
        this.updatedStatusMessage = message;
    }
}
