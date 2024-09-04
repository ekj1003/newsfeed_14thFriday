package com.sparta.newsfeed14thfriday.domain.user.controller;

import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserChangePwdRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserDeleteRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.response.*;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserProfileUpdateRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserStatusMessageRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.service.UserService;
import com.sparta.newsfeed14thfriday.entity_common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    //유저의 이메일을 기반으로 유저 정보를 조회합니다.
    @GetMapping("/user-management/{userEmail}/profiles")
    public ApiResponse<UserProfileResponseDto> getProfile(@PathVariable String userEmail) {
        UserProfileResponseDto  responseDto = userService.getProfile(userEmail);
        log.info("유저 프로필 조회");
        return ApiResponse.createSuccess("유저 프로필 조회 완료", HttpStatus.CREATED.value(), responseDto);
    }
    //유저의 정보를 수정합니다.
    @PutMapping("/user-management/{userEmail}/profiles")
    public ApiResponse<UserProfileUpdateResponseDto> updateProfile(@PathVariable String userEmail,
                                                                   @RequestBody UserProfileUpdateRequestDto requestDto) {
        UserProfileUpdateResponseDto responseDto = userService.updateProfile(userEmail,requestDto);
        log.info("유저 정보 수정");
        return ApiResponse.createSuccess("유저 프로필 업데이트 완료",HttpStatus.CREATED.value(),responseDto);
    }
    //유저의 상태메시지를 수정합니다.
    @PutMapping("/user-management/{userEmail}/profiles/status-messages")
    public ApiResponse<UserStatusMessageResponseDto> updateStatusMessage(
            @PathVariable String userEmail,
            @RequestBody UserStatusMessageRequestDto requestDto) {
        UserStatusMessageResponseDto responseDto = userService.updateStatusMessage(userEmail,requestDto);
        log.info("유저 상태 메시지 수정");
        return ApiResponse.createSuccess("유저 상태 메시지 업데이트 완료",HttpStatus.CREATED.value(), responseDto);

    }
    //유저를 삭제처리합니다.
    @PutMapping("/user-management/{userEmail}/profiles/delete-account")
    public ApiResponse<String> deleteUser(@PathVariable String userEmail,@RequestBody UserDeleteRequestDto requestDto) {
        userService.deleteUser(userEmail,requestDto);
        log.info("유저 삭제");
        return ApiResponse.createSuccess("유저 삭제 완료",HttpStatus.CREATED.value(), null);
    }

    @PutMapping("/{userEmail}/profiles/checked")
    public ApiResponse<String> changePassword(@PathVariable String userEmail,@RequestBody UserChangePwdRequestDto requestDto) {
        userService.changePwd(userEmail,requestDto);
        log.info("유저 비밀번호 변경");
        return ApiResponse.createSuccess("유저 비밀 번호 변경",HttpStatus.CREATED.value(), null);
    }

}
