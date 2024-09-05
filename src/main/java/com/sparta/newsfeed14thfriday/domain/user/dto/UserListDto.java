package com.sparta.newsfeed14thfriday.domain.user.dto;

import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserListDto {
    private final String email;
    private final String statusMessage;

    public UserListDto(User user) {
        this.email = user.getEmail();
        this.statusMessage = user.getStatusMessage();
    }
}
