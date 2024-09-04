package com.sparta.newsfeed14thfriday.domain.post.controller;

import com.sparta.newsfeed14thfriday.domain.post.dto.request.PostPeriodRequestDto;
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

    // 기간별 게시물 검색 기능
    //    {
    //        "startDate": "2024-09-04",
    //        "endDate": "2024-09-05"
    //    }
    // 형식으로 body를 보낼 경우 해당 기간 내에 생성된 게시물을
    // 최신으로 생성된 순(createAt 기준 내림차순)으로 보여준다.
    @GetMapping("/posts/period")
    public Page<PostSimpleResponseDto> getPostsPeriod(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestBody PostPeriodRequestDto periodRequestDto) {
        return postService.getPostsPeriod(page, size, periodRequestDto);
    }




    // 게시물 수정
    @PutMapping("/posts/{postId}")
    public PostUpdateResponseDto updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequestDto postUpdateRequestDto){
        return postService.updatePost(postId, postUpdateRequestDto);
    }




    // 게시물 삭제
    @DeleteMapping("/posts/{postId}")
    public void deleteUser(@PathVariable Long postId){
        postService.deletePost(postId);
    }




}
