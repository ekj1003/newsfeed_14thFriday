package com.sparta.newsfeed14thfriday.domain.post.dto.request;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {
    private Long postId;
    private String email;
    private String title;
    private String contents;
}
