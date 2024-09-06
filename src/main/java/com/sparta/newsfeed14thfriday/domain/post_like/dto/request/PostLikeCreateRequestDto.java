package com.sparta.newsfeed14thfriday.domain.post_like.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLikeCreateRequestDto {
    private String email;
    private Long postId;
}
