package com.sparta.newsfeed14thfriday.domain.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostDetailResponseDto {
    private Long postId;
    private String title;
    private String contents;
    private Long commentCount;
    private Long postLikeCount;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private String writer;
}
