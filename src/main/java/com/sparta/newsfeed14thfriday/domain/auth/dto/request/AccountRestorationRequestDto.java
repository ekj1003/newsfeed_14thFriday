package com.sparta.newsfeed14thfriday.domain.auth.dto.request;

import lombok.Getter;

@Getter
public class AccountRestorationRequestDto {
    private String userEmail;
    private String password;
}
