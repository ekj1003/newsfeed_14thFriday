package com.sparta.newsfeed14thfriday.domain.post.dto.response;

import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import lombok.Getter;

@Getter
public class PostSimpleResponseDto {
    private final String title;
    private final Long commentCount;
    private final Long postLikeCount;

    public PostSimpleResponseDto(Post post) {
        this.title = post.getTitle();
        this.commentCount = post.getCommentCount();
        this.postLikeCount = post.getPostLikeCount();
    }
}
