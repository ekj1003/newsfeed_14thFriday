package com.sparta.newsfeed14thfriday.domain.user.dto.response;

import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import lombok.Getter;

@Getter
public class UserGetPostsResponseDto {
    private final String title;
    private final Long commentCount;
    private final Long postLikeCount;

    public UserGetPostsResponseDto(Post post) {
        this.title = post.getTitle();
        this.commentCount = post.getCommentCount();
        this.postLikeCount = post.getPostLikeCount();
    }
}
