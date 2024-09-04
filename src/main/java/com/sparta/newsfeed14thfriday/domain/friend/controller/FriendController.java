package com.sparta.newsfeed14thfriday.domain.friend.controller;

import com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList.FriendListResponseDto;
import com.sparta.newsfeed14thfriday.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @GetMapping("/list")
    public List<FriendListResponseDto> getUsers() {
        return friendService.getFriend();
    }

    @GetMapping("/list/{friendId}")
    public FriendListResponseDto getUser(@PathVariable Long friendId) {
        return friendService.getFriends(friendId);
    }




}
