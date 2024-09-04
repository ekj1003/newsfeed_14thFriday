package com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList;

import lombok.Getter;

@Getter
public class FriendListRequestDto {
    private final String userEmail;
    private final String friendEmail;

    public FriendListRequestDto(String userEmail, String friendEmail) {
        this.userEmail = userEmail;
        this.friendEmail = friendEmail;
    }
}
