package com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserEmailRequestDto {
    private String userEmail;

    public UserEmailRequestDto(String userEmail) {
        this.userEmail = userEmail;
    }
}