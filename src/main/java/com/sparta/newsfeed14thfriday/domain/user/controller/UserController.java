package com.sparta.newsfeed14thfriday.domain.user.controller;

import com.sparta.newsfeed14thfriday.domain.user.dto.*;
import com.sparta.newsfeed14thfriday.domain.user.service.UserService;
import com.sparta.newsfeed14thfriday.entity_common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    //유저의 이메일을 기반으로 유저 정보를 조회합니다.
    @GetMapping("/{userEmail}/profile")
    public ApiResponse<UserProfileResponseDto> getProfile(@PathVariable String userEmail) {
        UserProfileResponseDto  responseDto = userService.getProfile(userEmail);
        return ApiResponse.createSuccess("유저 프로필 조회 완료",HttpStatus.CREATED.value(), responseDto);
    }
    //유저의 정보를 수정합니다.
    @PutMapping("/{userEmail}/profile")
    public ApiResponse<UserProfileResponseDto> updateProfile(@PathVariable String userEmail) {
        UserProfileResponseDto  responseDto = userService.updateProfile(userEmail);
        return ApiResponse.createSuccess("유저 프로필 업데이트 완료",HttpStatus.CREATED.value(), responseDto);
    }
    //유저의 상태메시지를 수정합니다.
    @PutMapping("/{userEmail}/profile/statusMessage")
    public ApiResponse<UserStatusMessageResponseDto> updateStatusMessage(@PathVariable String userEmail, @RequestBody
    UserStatusMessageRequestDto requestDto) {
        UserStatusMessageResponseDto responseDto = userService.updateStatusMessage(userEmail,requestDto);
        return ApiResponse.createSuccess("유저 상태 메시지 업데이트 완료",HttpStatus.CREATED.value(), responseDto);

    }


}
