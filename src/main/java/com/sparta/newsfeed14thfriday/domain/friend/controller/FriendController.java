package com.sparta.newsfeed14thfriday.domain.friend.controller;

import com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList.FriendListRequestDto;
import com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList.FriendListResponseDto;
import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    // 친구 저장
    @PostMapping("/friends")
    public ResponseEntity<FriendListResponseDto> createFriend(@RequestBody FriendListRequestDto requestDto) {
        Friend savedFriend = friendService.saveFriend(requestDto);
        FriendListResponseDto dto = new FriendListResponseDto(
                savedFriend.getId(),
                savedFriend.getFriend().getEmail() // User 객체에서 이메일 추출
        );
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // 친구 리스트 조회
    @GetMapping("/friends")
    public ResponseEntity<List<FriendListResponseDto>> getFriendList(@RequestParam String userEmail) {
        List<FriendListResponseDto> friends = friendService.getFriendList(userEmail);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    // 특정 친구 조회
    @GetMapping("/friends/{id}")
    public ResponseEntity<FriendListResponseDto> getFriendById(@PathVariable("id") Long friendId) {
        FriendListResponseDto friend = friendService.getFriendById(friendId);
        return new ResponseEntity<>(friend, HttpStatus.OK);
    }


}
