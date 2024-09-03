package com.sparta.newsfeed14thfriday.post.controller;

import com.sparta.newsfeed14thfriday.post.dto.request.PostSaveRequestDto;
import com.sparta.newsfeed14thfriday.post.dto.response.PostSaveResponseDto;
import com.sparta.newsfeed14thfriday.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시물 생성
    @PostMapping("/posts")
    public PostSaveResponseDto createPost(@RequestBody PostSaveRequestDto postSaveRequestDto){
        return postService.createPost(postSaveRequestDto);
    }


    // 게시물 단건 조회


    // 게시물 전체 조회(page)



    // 게시물 수정




    // 게시물 삭제




}
