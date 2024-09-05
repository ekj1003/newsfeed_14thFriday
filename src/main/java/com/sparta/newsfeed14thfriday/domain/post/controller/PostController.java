package com.sparta.newsfeed14thfriday.domain.post.controller;

import com.sparta.newsfeed14thfriday.domain.post.dto.request.PostDeleteRequestDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.request.PostSaveRequestDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.request.PostUpdateRequestDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostDetailResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostSaveResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostSimpleResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostUpdateResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    @GetMapping("/posts/users/{userEmail}")
    public Page<PostSimpleResponseDto> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10")int size,
            @PathVariable String userEmail) {
        return postService.getPosts(page,size,userEmail);
    }



    // 게시물 수정
    @PutMapping("/posts/{postId}")
    public PostUpdateResponseDto updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequestDto postUpdateRequestDto){
        return postService.updatePost(postId, postUpdateRequestDto);
    }




    // 게시물 삭제
    @DeleteMapping("/posts/{postId}")
    public void deleteUser(@PathVariable Long postId, @RequestBody PostDeleteRequestDto postDeleteRequestDto){
        postService.deletePost(postId, postDeleteRequestDto);
    }




}
