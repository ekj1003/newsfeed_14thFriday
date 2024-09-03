package com.sparta.newsfeed14thfriday.domain.user.controller;


import com.sparta.newsfeed14thfriday.domain.user.dto.request.LoginRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.SignupRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.response.*;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserProfileUpdateRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserStatusMessageRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
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
    // 로그인
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        try {
            userService.login(requestDto, res);
        } catch (Exception e) {
            return ApiResponse.createError("아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.",
                    HttpStatus.BAD_REQUEST.value());
        }

        return ApiResponse.createSuccess("로그인에 성공했습니다. 환영합니다!",HttpStatus.CREATED.value(),null);
    }
    //유저의 이메일을 기반으로 유저 정보를 조회합니다.
    @GetMapping("/{userEmail}/profile")
    public ApiResponse<UserProfileResponseDto> getProfile(@PathVariable String userEmail) {
        UserProfileResponseDto  responseDto = userService.getProfile(userEmail);
        return ApiResponse.createSuccess("유저 프로필 조회 완료",HttpStatus.CREATED.value(), responseDto);
    }
    //유저의 정보를 수정합니다.
    @PutMapping("/{userEmail}/profile")
    public ApiResponse<UserProfileUpdateResponseDto> updateProfile(@PathVariable String userEmail,
                                                                   @RequestBody UserProfileUpdateRequestDto requestDto) {
        UserProfileUpdateResponseDto responseDto = userService.updateProfile(userEmail,requestDto);
        return ApiResponse.createSuccess("유저 프로필 업데이트 완료",HttpStatus.CREATED.value(),responseDto);
    }
    //유저의 상태메시지를 수정합니다.
    @PutMapping("/{userEmail}/profile/statusMessage")
    public ApiResponse<UserStatusMessageResponseDto> updateStatusMessage(
            @PathVariable String userEmail,
            @RequestBody UserStatusMessageRequestDto requestDto) {
        UserStatusMessageResponseDto responseDto = userService.updateStatusMessage(userEmail,requestDto);
        return ApiResponse.createSuccess("유저 상태 메시지 업데이트 완료",HttpStatus.CREATED.value(), responseDto);

    }
    @PutMapping("/{userEmail}")
    public ApiResponse<String> deleteUser(@PathVariable String userEmail) {
        userService.deleteUser(userEmail);
        return ApiResponse.createSuccess("유저 삭제 완료",HttpStatus.CREATED.value(), null);
    }

}
