package com.sparta.newsfeed14thfriday.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSaveResponseDto {
    private String message;
    private Integer statusCode;
    private Long postId;

}
