package com.sparta.newsfeed14thfriday.domain.comment_like.controller;

import com.sparta.newsfeed14thfriday.domain.comment_like.dto.request.CommentLikeRequestDto;
import com.sparta.newsfeed14thfriday.domain.comment_like.dto.response.CommentLikeResponseDto;
import com.sparta.newsfeed14thfriday.domain.comment_like.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;


    @PostMapping("/posts/{postId}/comments/{commentId}/likes")
    public CommentLikeResponseDto addCommentLike(@PathVariable Long postId, @PathVariable Long commentId,@RequestBody CommentLikeRequestDto commentLikeRequestDto) {
        return commentLikeService.createCommentLike(commentId , postId, commentLikeRequestDto);
    }


    @PutMapping("/posts/{postId}/comments/{commentId}/likes/delete")
    public void deleteCommentLike(@PathVariable Long commentId, @PathVariable Long postId, @RequestBody CommentLikeRequestDto commentLikeRequestDto) {
        commentLikeService.deleteCommentLike(commentId, postId, commentLikeRequestDto);
    }

}
