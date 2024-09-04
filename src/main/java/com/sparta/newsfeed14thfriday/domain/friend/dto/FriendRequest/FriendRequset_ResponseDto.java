package com.sparta.newsfeed14thfriday.domain.friend.dto.FriendRequest;

import com.sparta.newsfeed14thfriday.domain.user.entity.User;

public class FriendRequset_ResponseDto {
    private final Long friend_id;
    private final User email;
    private final boolean friend_stated;

    public FriendRequset_ResponseDto(Long friendId, User email, boolean friendStated) {
        friend_id = friendId;
        this.email = email;
        friend_stated = friendStated;
    }
}
