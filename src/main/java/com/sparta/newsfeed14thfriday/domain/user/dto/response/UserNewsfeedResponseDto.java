package com.sparta.newsfeed14thfriday.domain.user.dto.response;

import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserNewsfeedResponseDto {
    private final String email;
    private final String title;
    private final Long commentCount;
    private final Long postLikeCount;

    public UserNewsfeedResponseDto(Post post){
        this.email = post.getUser().getEmail();
        this.title = post.getTitle();
        this.commentCount = post.getCommentCount();
        this.postLikeCount = post.getPostLikeCount();
    }
}
