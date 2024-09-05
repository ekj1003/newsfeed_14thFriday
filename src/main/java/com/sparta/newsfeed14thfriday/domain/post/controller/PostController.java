package com.sparta.newsfeed14thfriday.domain.post.controller;

import com.sparta.newsfeed14thfriday.domain.post.dto.request.PostDeleteRequestDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.request.PostSaveRequestDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.request.PostUpdateRequestDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostDetailResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostSaveResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostSimpleResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostUpdateResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.service.PostService;
import com.sparta.newsfeed14thfriday.entity_common.ApiPageResponse;
import com.sparta.newsfeed14thfriday.entity_common.ApiResponse;
import com.sparta.newsfeed14thfriday.global.config.TokenUserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시물 생성
    @PostMapping("/posts")
    public ApiResponse<PostSaveResponseDto> createPost(@TokenUserEmail String Token,
                                                      @RequestBody PostSaveRequestDto postSaveRequestDto){
        PostSaveResponseDto responseDto = postService.createPost(Token,postSaveRequestDto);
        return ApiResponse.createSuccess("게시물 생성 완료", HttpStatus.CREATED.value(),responseDto);
    }

    // 게시물 단건 조회
    @GetMapping("/posts/{postId}")
    public ApiResponse<PostDetailResponseDto> getPost(@PathVariable Long postId){
        PostDetailResponseDto responseDto = postService.getPost(postId);
        return ApiResponse.createSuccess("게시물 조회 완료", HttpStatus.CREATED.value(),responseDto);
    }

    // 게시물 전체 조회(page) -> notion API 추가필요
    //@GetMapping("/posts")
    @GetMapping("/posts/users/{userEmail}")
    public ApiPageResponse<PostSimpleResponseDto> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10")int size,
            @PathVariable String userEmail) {
        Page<PostSimpleResponseDto> responseDtoPage = postService.getPosts(page,size,userEmail);
        return ApiPageResponse.createSuccess("게시물 전체 조회 완료",HttpStatus.CREATED.value(),responseDtoPage);
    }



    // 게시물 수정
    @PutMapping("/posts/{postId}")
    public ApiResponse<PostUpdateResponseDto> updatePost(
            @TokenUserEmail String Token,
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto postUpdateRequestDto){
        PostUpdateResponseDto responseDto = postService.updatePost(postId,Token, postUpdateRequestDto);
        return ApiResponse.createSuccess("게시물 수정 완료",HttpStatus.CREATED.value(),responseDto);
    }




    // 게시물 삭제
    @DeleteMapping("/posts/{postId}")
    public ApiResponse<String> deleteUser(
            @TokenUserEmail String Token,
            @PathVariable Long postId,
            @RequestBody PostDeleteRequestDto postDeleteDto){
        String message = "삭제 완료";
        postService.deletePost(Token,postId, postDeleteDto);
        return ApiResponse.createSuccess("게시물 삭제 완료,",HttpStatus.CREATED.value(),message);
    }




}
