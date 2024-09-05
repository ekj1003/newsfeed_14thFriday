package com.sparta.newsfeed14thfriday.domain.user.dto.response;

import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserProfileSimpleResponseDto {
    private final String email;
    private final String statusMessage;
    private final String username;

    public UserProfileSimpleResponseDto(User user) {
        this.email = user.getEmail();
        this.statusMessage = user.getStatusMessage();
        this.username = user.getUsername();
    }
}
