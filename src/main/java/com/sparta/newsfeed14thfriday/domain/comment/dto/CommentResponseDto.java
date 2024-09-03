package com.sparta.newsfeed14thfriday.domain.comment.dto;

import com.sparta.newsfeed14thfriday.domain.comment.entity.Comment;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String contents;
    private Long commentLikeCount;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User user;


    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.contents = comment.getContents();
        this.commentLikeCount = comment.getCommentLikeCount();
        this.deleted = comment.getDeleted();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

}
