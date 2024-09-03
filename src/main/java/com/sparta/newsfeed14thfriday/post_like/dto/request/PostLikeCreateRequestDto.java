package com.sparta.newsfeed14thfriday.post_like.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLikeCreateRequestDto {
    private Long userId;
    private Long postId;
}
