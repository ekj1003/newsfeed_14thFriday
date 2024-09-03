package com.sparta.newsfeed14thfriday.domain.comment.controller;


import com.sparta.newsfeed14thfriday.domain.comment.service.CommentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

//    @PostMapping("/comments")
//    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto) {
//        return commentService.createComment(requestDto);
//    }



}
