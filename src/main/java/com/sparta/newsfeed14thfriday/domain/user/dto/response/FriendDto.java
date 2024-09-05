package com.sparta.newsfeed14thfriday.domain.user.dto.response;

import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendDto {
    private Long id;
    private String email;
    private String statusMessage;

    @Builder
    public FriendDto(Long id, String email, String statusMessage) {
        this.id = id;
        this.email = email;
        this.statusMessage = statusMessage;
    }

    public static FriendDto ofDto(Friend friend) {
        return FriendDto.builder()
                .id(friend.getId())
                .statusMessage(friend.getStatus())
                .build();
    }
}
