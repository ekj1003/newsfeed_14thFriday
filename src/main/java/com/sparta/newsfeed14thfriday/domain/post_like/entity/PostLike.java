package com.sparta.newsfeed14thfriday.domain.post_like.entity;

import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    private Long postLikeId;

    // 양방향 -> 나중에 좋아요한 게시물만 불러오고싶어서
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public static PostLike createPostLike( Post post) { //User user,
        PostLike postLike = new PostLike();
        postLike.post = post;
        return postLike;
    }


//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "userId", nullable = false)
//    private User user;
}
