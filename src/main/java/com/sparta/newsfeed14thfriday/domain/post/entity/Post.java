package com.sparta.newsfeed14thfriday.domain.post.entity;

import com.sparta.newsfeed14thfriday.domain.post_like.entity.PostLike;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String contents;

    @Column(length = 100, nullable = false)
    private String writer;
    //   @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    //  private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<PostLike> postLike;

    private Long commentCount=0L;

    private Long postLikeCount=0L;

    // 유저 아이디 FK
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "email", nullable = false)
    private User user;

    public static Post createNewPost(String title, String contents,String writer, User user) {
        Post newPost = new Post();
        newPost.title = title;
        newPost.contents = contents;
        newPost.user = user;
        newPost.writer = writer;

        return newPost;
    }



    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void updatePostLikeCount() {

        this.postLikeCount = (this.postLikeCount == null ? 0L : postLikeCount) + 1;
    }
}
