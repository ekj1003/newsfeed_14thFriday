package com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList;

import com.sparta.newsfeed14thfriday.domain.user.entity.User;

public class FriendListResponseDto {
    private final Long friend_id;
    private final User email;

    public FriendListResponseDto(Long friendId, User email) {
        this.friend_id = friendId;
        this.email = email;
    }
}
