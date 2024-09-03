package com.sparta.newsfeed14thfriday.domain.comment_like.controller;

import com.sparta.newsfeed14thfriday.domain.comment_like.dto.request.CommentLikeRequestDto;
import com.sparta.newsfeed14thfriday.domain.comment_like.dto.response.CommentLikeResponseDto;
import com.sparta.newsfeed14thfriday.domain.comment_like.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;


//    @PostMapping("/comments/{commentId}/likes")
//    public CommentLikeResponseDto addCommentLike(@PathVariable Long commentId, @RequestBody CommentLikeRequestDto commentLikeRequestDto) {
//        return commentLikeService.createCommentLike(commentId, commentLikeRequestDto);
//    }

}
