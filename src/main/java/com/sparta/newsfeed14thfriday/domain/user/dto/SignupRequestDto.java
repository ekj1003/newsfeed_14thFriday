package com.sparta.newsfeed14thfriday.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String username;
    private String password;

    // 이메일 형식 유효성 검사
    public boolean isEmailValid() {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, this.email);
    }

    // 비밀번호 형식 유효성 검사
    public boolean isPasswordValid() {
        // 최소 8자, 대소문자, 숫자, 특수문자 각각 최소 1개 포함
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(passwordRegex, this.password);
    }
}
