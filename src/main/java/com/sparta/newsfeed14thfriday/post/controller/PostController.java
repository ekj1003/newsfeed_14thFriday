package com.sparta.newsfeed14thfriday.post.controller;

import com.sparta.newsfeed14thfriday.post.dto.request.PostSaveRequestDto;
import com.sparta.newsfeed14thfriday.post.dto.request.PostUpdateRequestDto;
import com.sparta.newsfeed14thfriday.post.dto.response.PostDetailResponseDto;
import com.sparta.newsfeed14thfriday.post.dto.response.PostSaveResponseDto;
import com.sparta.newsfeed14thfriday.post.dto.response.PostUpdateResponseDto;
import com.sparta.newsfeed14thfriday.post.entity.Post;
import com.sparta.newsfeed14thfriday.post.service.PostService;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/posts/{postId}")
    public PostDetailResponseDto getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }


    // 게시물 전체 조회(page) -> notion API 추가필요
    //@GetMapping("/posts")



    // 게시물 수정
    @PutMapping("/posts/{postId}")
    public PostUpdateResponseDto updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequestDto postUpdateRequestDto){
        return postService.updatePost(postId, postUpdateRequestDto);
    }




    // 게시물 삭제
    //@DeleteMapping("/posts/{postId}")




}
