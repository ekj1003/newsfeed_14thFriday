package com.sparta.newsfeed14thfriday.post.controller;

import com.sparta.newsfeed14thfriday.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
}
