package com.sparta.newsfeed14thfriday.domain.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    private String userEmail;
    private String contents;

}
