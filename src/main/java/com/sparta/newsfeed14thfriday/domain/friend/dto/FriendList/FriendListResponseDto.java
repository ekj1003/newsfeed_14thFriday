package com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList;

import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import lombok.Getter;

@Getter
public class FriendListResponseDto {
    private final Long friend_id;
    private final String friendEmail; // User 객체 대신 이메일 반환

    public FriendListResponseDto(Long friendId, String friendEmail) {
        this.friend_id = friendId;
        this.friendEmail = friendEmail;
    }
}
