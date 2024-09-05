package com.sparta.newsfeed14thfriday.domain.comment.dto.response;

import com.sparta.newsfeed14thfriday.domain.comment.entity.Comment;
import com.sparta.newsfeed14thfriday.domain.user.dto.UserDto;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentSaveResponseDto {

    private Long commentId;
    private String contents;
    private Long commentLikeCount;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UserDto user;


    public CommentSaveResponseDto(Long commentId, User email, String contents, Long commentLikeCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.contents = contents;
        this.commentLikeCount = commentLikeCount;

        this.user = new UserDto(
                email.getEmail()
        );

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
