package com.sparta.newsfeed14thfriday.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UserChangePwdRequestDto {
    private String oldPassword;
    private String newPassword;
}
