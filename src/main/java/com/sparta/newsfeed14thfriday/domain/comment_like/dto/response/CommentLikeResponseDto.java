package com.sparta.newsfeed14thfriday.domain.comment_like.dto.response;

import lombok.Getter;

@Getter
public class CommentLikeResponseDto {

    private Long commentLikeId;

    public CommentLikeResponseDto(Long commentLikeId) {
        this.commentLikeId = commentLikeId;
    }

}
