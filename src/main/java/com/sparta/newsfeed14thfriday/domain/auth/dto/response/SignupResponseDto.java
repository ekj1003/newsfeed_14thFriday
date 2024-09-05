package com.sparta.newsfeed14thfriday.domain.auth.dto.response;

import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private final String bearerToken;

    public SignupResponseDto(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
