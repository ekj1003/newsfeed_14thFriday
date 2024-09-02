package com.sparta.newsfeed14thfriday.domain.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private long commentId;
    private String contents;
    private long commentLikeCount;
    private Boolean deleted;

    private long userId;
    private long postId;

}
