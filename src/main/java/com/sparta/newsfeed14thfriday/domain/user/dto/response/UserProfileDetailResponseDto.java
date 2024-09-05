package com.sparta.newsfeed14thfriday.domain.user.dto.response;

import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserProfileDetailResponseDto {

    private final String email;
    private final String statusMessage;
    private final String username;



    public UserProfileDetailResponseDto(User user, List<Friend> friendsList) {
        this.email = user.getEmail();
        this.statusMessage = user.getStatusMessage();
        this.username = user.getUsername();

    }

    public UserProfileDetailResponseDto(User user) {
        this.email = user.getEmail();
        this.statusMessage = user.getStatusMessage();
        this.username = user.getUsername();
    }
}
