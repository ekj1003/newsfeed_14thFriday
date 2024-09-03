package com.sparta.newsfeed14thfriday.domain.post_like.controller;

import com.sparta.newsfeed14thfriday.domain.post_like.dto.request.PostLikeCreateRequestDto;
import com.sparta.newsfeed14thfriday.domain.post_like.dto.response.PostLikeCreateResponseDto;
import com.sparta.newsfeed14thfriday.domain.post_like.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    // 좋아요 생성
    @PostMapping("/posts/{postId}/likes")
    public PostLikeCreateResponseDto createPostLike(@PathVariable Long postId, @RequestBody PostLikeCreateRequestDto postLikeCreateRequestDto){
        return postLikeService.createPostLike(postId, postLikeCreateRequestDto);
    }

    // 좋아요 삭제
    //@DeleteMapping("/posts/{postId}/likes/{likesId}")
}
