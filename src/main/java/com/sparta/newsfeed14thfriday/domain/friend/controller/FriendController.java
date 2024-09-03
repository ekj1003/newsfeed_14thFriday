package com.sparta.newsfeed14thfriday.domain.friend.controller;

import com.sparta.newsfeed14thfriday.domain.friend.service.FriendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friends")
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }


}
