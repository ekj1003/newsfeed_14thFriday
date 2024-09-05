package com.sparta.newsfeed14thfriday.domain.friend.controller;

import com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList.FriendListRequestDto;
import com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList.FriendListResponseDto;
import com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList.UserEmailRequestDto;
import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.domain.friend.service.FriendService;
import com.sparta.newsfeed14thfriday.global.config.TokenUserEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/friends")
public class FriendController {
    private final FriendService friendService;

    // 친구 저장
    @PostMapping
    public ResponseEntity<FriendListResponseDto> createFriend(@TokenUserEmail String token,
            @RequestBody FriendListRequestDto requestDto) {
        try {
            Friend savedFriend = friendService.saveFriend(token,requestDto);
            // 저장된 친구 정보를 FriendListResponseDto로 반환
            FriendListResponseDto dto = new FriendListResponseDto(
                    savedFriend.getId(),
                    savedFriend.getFriend().getEmail() // 친구의 이메일 반환
            );
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // 중복 친구 요청일 경우 에러 메시지 반환
            log.info("중복 친구 요청입니다.");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 친구 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<List<FriendListResponseDto>> getFriendList(@RequestBody UserEmailRequestDto requestDto) {
        List<FriendListResponseDto> friends = friendService.getFriendList(requestDto.getUserEmail());
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    // 특정 친구 조회
    @GetMapping("/details")
    public ResponseEntity<FriendListResponseDto> getFriendByEmail(@RequestBody FriendListRequestDto requestDto) {
        FriendListResponseDto friend = friendService.getFriendByEmail(requestDto.getUserEmail(), requestDto.getFriendEmail());
        return new ResponseEntity<>(friend, HttpStatus.OK);
    }

    // 친구 요청 수락
    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@TokenUserEmail String token,
            @RequestBody FriendListRequestDto requestDto) {
        friendService.acceptFriendRequest(token,requestDto.getUserEmail(), requestDto.getFriendEmail());
        return new ResponseEntity<>("친구 추가 수락", HttpStatus.OK);
    }

    // 친구 요청 거절
    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@TokenUserEmail String token,
            @RequestBody FriendListRequestDto requestDto) {
        friendService.rejectFriendRequest(token,requestDto.getUserEmail(), requestDto.getFriendEmail());
        return new ResponseEntity<>("친구 추가 거절", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFriend(@TokenUserEmail String token,
            @RequestBody FriendListRequestDto requestDto) {
        friendService.deleteFriend(token,requestDto.getUserEmail(), requestDto.getFriendEmail());
        return new ResponseEntity<>("친구 삭제", HttpStatus.NO_CONTENT);
    }
}
