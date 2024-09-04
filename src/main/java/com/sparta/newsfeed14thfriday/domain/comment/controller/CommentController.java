package com.sparta.newsfeed14thfriday.domain.comment.controller;


import com.sparta.newsfeed14thfriday.domain.comment.dto.request.CommentDeleteRequestDto;
import com.sparta.newsfeed14thfriday.domain.comment.dto.request.CommentSaveRequestDto;
import com.sparta.newsfeed14thfriday.domain.comment.dto.request.CommentUpdateRequestDto;
import com.sparta.newsfeed14thfriday.domain.comment.dto.response.CommentDetailResponseDto;
import com.sparta.newsfeed14thfriday.domain.comment.dto.response.CommentSaveResponseDto;
import com.sparta.newsfeed14thfriday.domain.comment.dto.response.CommentUpdateResponseDto;
import com.sparta.newsfeed14thfriday.domain.comment.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 어느 포스트에 코멘트를 작성할건지 API 변경 필요
    @PostMapping("/posts/{postId}/comments")
    public CommentSaveResponseDto createComment(@PathVariable Long postId, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return commentService.createComment(postId, commentSaveRequestDto);
    }

    // 어느 포스트의 코멘트를 조회할건지 전체 조회 기능보다 이게 나을 듯?
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDetailResponseDto> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    // 어느 코멘트를 수정할지
    @PutMapping("/comments/{commentId}")
    public CommentUpdateResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return commentService.updateComment(commentId, commentUpdateRequestDto);
    }

//    @DeleteMapping("/comments/{commentId}")
//    public void deleteComment(@PathVariable Long commentId) {
//        commentService.deleteComment(commentId);
//    } // 아예 삭제하는 버전

    @PutMapping("/comments/{commentId}/delete")
    public void deleteCommentUpdate(@PathVariable Long commentId, @RequestBody CommentDeleteRequestDto commentDeleteRequestDto) {
        commentService.deleteCommentUpdate(commentId, commentDeleteRequestDto);
    }



}
