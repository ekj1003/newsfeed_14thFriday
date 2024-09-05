package com.sparta.newsfeed14thfriday.domain.user.dto;

import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import lombok.Getter;

@Getter
public class RequestToMeDto {
    private final String email;
    private final String status;

    public RequestToMeDto(User user,String status) {
        if(status.equals("receive")) {
            this.email = user.getEmail();
            this.status = status;
        }
        else {
            this.email = user.getEmail();
            this.status = status;
        }
    }
}
