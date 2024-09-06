package com.sparta.newsfeed14thfriday.domain.user.dto.response;

import com.sparta.newsfeed14thfriday.domain.user.dto.RequestToMeDto;
import lombok.Getter;

import java.util.List;

@Getter
public class UserFriendRequestResponseDto {
    private final List<RequestToMeDto> receiveDtos;
    private final List<RequestToMeDto> sendDtos;

    public UserFriendRequestResponseDto(List<RequestToMeDto> receiveDtos, List<RequestToMeDto> sendDtos) {
        this.receiveDtos = receiveDtos;
        this.sendDtos = sendDtos;
    }
}
