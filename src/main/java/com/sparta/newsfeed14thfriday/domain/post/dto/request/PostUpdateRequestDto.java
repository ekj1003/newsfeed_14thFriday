package com.sparta.newsfeed14thfriday.domain.post.dto.request;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {
    private Long postId;
    private Long userId;
    private String title;
    private String contents;
}
