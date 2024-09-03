package com.sparta.newsfeed14thfriday.domain.user.controller;

import com.sparta.newsfeed14thfriday.domain.user.dto.SignupRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.SignupResponseDto;
import com.sparta.newsfeed14thfriday.domain.user.service.UserService;
import com.sparta.newsfeed14thfriday.entity_common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    // 회원 가입
    @PostMapping("/signup")
    public ApiResponse<SignupResponseDto> signup(@RequestBody SignupRequestDto requestDto) {
        SignupResponseDto responseDto = userService.signup(requestDto);
        return ApiResponse.createSuccess("회원가입 완료", HttpStatus.CREATED.value(), responseDto);
    }


}
