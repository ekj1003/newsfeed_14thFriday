package com.sparta.newsfeed14thfriday.domain.auth.controller;

import com.sparta.newsfeed14thfriday.domain.auth.dto.request.LoginRequestDto;
import com.sparta.newsfeed14thfriday.domain.auth.dto.request.SignupRequestDto;
import com.sparta.newsfeed14thfriday.domain.auth.dto.response.LoginResponseDto;
import com.sparta.newsfeed14thfriday.domain.auth.dto.response.SignupResponseDto;
import com.sparta.newsfeed14thfriday.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원 가입
    // 토큰 반환한다.
    @PostMapping("/auth/signup")
    public SignupResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        return authService.signup(signupRequestDto);
    }

    // 로그인
    // 토큰 반환한다.
    @PostMapping("/auth/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

}
