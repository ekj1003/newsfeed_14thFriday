package com.sparta.newsfeed14thfriday.domain.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
