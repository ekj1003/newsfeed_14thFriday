package com.sparta.newsfeed14thfriday.domain.user.dto.response;

import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserProfileResponseDto {

    private final String email;
    private final String statusMessage;
    private final String username;
    private final Long friendsCount;
    private final List<Friend> friendsList;

    public UserProfileResponseDto(User user,String Type) {
        if (Type.equals("Detail")) {
            this.email = user.getEmail();
            this.statusMessage = user.getStatusMessage();
            this.username = user.getUsername();
            this.friendsCount = user.getFriendsCount();
            this.friendsList = user.getFriends();
        } else {
            this.email = user.getEmail();
            this.statusMessage = user.getStatusMessage();
            this.username = user.getUsername();
            this.friendsCount = null;
            this.friendsList = null;
        }
    }
}
