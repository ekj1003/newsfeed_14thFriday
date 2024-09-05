package com.sparta.newsfeed14thfriday.domain.user.controller;

import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserChangePwdRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserDeleteRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.response.*;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserProfileUpdateRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserStatusMessageRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.service.UserService;
import com.sparta.newsfeed14thfriday.entity_common.ApiPageResponse;
import com.sparta.newsfeed14thfriday.entity_common.ApiResponse;
import com.sparta.newsfeed14thfriday.global.config.TokenUserEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    //뉴스피드
    @GetMapping("/newsfeed/{userEmail}")
    public ApiPageResponse<UserNewsfeedResponseDto> getNewsfeed(
            @PathVariable String userEmail,
            @TokenUserEmail String token,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserNewsfeedResponseDto> responseDtoPage = userService.getNewsfeed(page,size,userEmail,token);
            return ApiPageResponse.createSuccess("뉴스피드 조회 완료",HttpStatus.CREATED.value(),responseDtoPage);

    }
    //유저의 이메일을 기반으로 유저 정보를 조회합니다.
    //필수 :- **프로필 조회 기능**
    //    - 다른 사용자의 프로필 조회 시, 민감한 정보는 표시되지 않습니다.
    @GetMapping("/user-management/{userEmail}/profiles")
    public ApiResponse<UserProfileDetailResponseDto> getProfile(
            @PathVariable String userEmail,
            @TokenUserEmail String tokenEmail) {
        UserProfileDetailResponseDto responseDto = userService.getProfile(tokenEmail,userEmail);
        log.info("유저 프로필 조회");
        return ApiResponse.createSuccess("유저 프로필 조회 완료", HttpStatus.CREATED.value(), responseDto);
    }
    @GetMapping("/{userEmail}/posts")
    public ApiPageResponse<UserGetPostsResponseDto> getUserPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable String userEmail) {
        Page<UserGetPostsResponseDto> responseDtoPage = userService.getUserPosts(page,size,userEmail);
        return ApiPageResponse.createSuccess("유저의 포스트 조회 완료",HttpStatus.CREATED.value(),responseDtoPage);
    }
    //유저의 정보를 수정합니다.
    //- 비밀번호 수정 조건
    //    - 비밀번호 수정 시, 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우에만 수정할 수 있습니다.
    //    - 현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.
    // @Request String userName
    @PutMapping("/user-management/{pathUserEmail}/profiles")
    public ApiResponse<UserProfileUpdateResponseDto> updateProfile(
            @PathVariable String pathUserEmail,
            @TokenUserEmail String userEmail, //Token에서 쪼갠 claim에 존재하는 Email을 리턴받습니다.
            @RequestBody UserProfileUpdateRequestDto requestDto) {
        UserProfileUpdateResponseDto responseDto = userService.updateProfile(pathUserEmail,userEmail,requestDto);

        log.info("유저 정보 수정");

        return ApiResponse.createSuccess("유저 프로필 업데이트 완료",HttpStatus.CREATED.value(),responseDto);
    }
    //유저의 상태메시지를 수정합니다.
    @PutMapping("/user-management/{pathUserEmail}/profiles/status-messages")
    public ApiResponse<UserStatusMessageResponseDto> updateStatusMessage(
            @PathVariable String pathUserEmail,
            @TokenUserEmail String userEmail,
            @RequestBody UserStatusMessageRequestDto requestDto) {
        UserStatusMessageResponseDto responseDto = userService.updateStatusMessage(pathUserEmail,userEmail,requestDto);
        log.info("유저 상태 메시지 수정");
        return ApiResponse.createSuccess("유저 상태 메시지 업데이트 완료",HttpStatus.CREATED.value(), responseDto);

    }
    //유저를 삭제처리합니다.
    @DeleteMapping("/user-management/{userEmail}/profiles/delete-account")
    public ApiResponse<String> deleteUser(
            @PathVariable String userEmail,
            @RequestBody UserDeleteRequestDto requestDto) {
        userService.deleteUser(userEmail,requestDto);
        log.info("유저 삭제");
        return ApiResponse.createSuccess("유저 삭제 완료",HttpStatus.CREATED.value(), null);
    }

    @PutMapping("/user-management/{userEmail}/profiles/checked")
    public ApiResponse<String> changePassword(
            @PathVariable String userEmail,
            @RequestBody UserChangePwdRequestDto requestDto) {
        userService.changePwd(userEmail,requestDto);
        log.info("유저 비밀번호 변경");
        return ApiResponse.createSuccess("유저 비밀 번호 변경",HttpStatus.CREATED.value(), null);
    }

}
