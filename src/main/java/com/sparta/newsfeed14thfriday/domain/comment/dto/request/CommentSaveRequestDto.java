package com.sparta.newsfeed14thfriday.domain.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentSaveRequestDto {

    private long commentId;
    private String contents;

    private String userEmail;
    private long postId;

}
