package com.sparta.newsfeed14thfriday.post_like.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLikeCreateResponseDto {
    private String message;
    private Integer statusCode;
    private Long PostLikeId;
}
