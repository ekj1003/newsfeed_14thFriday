package com.sparta.newsfeed14thfriday.domain.comment_like.entity;

import com.sparta.newsfeed14thfriday.domain.comment.entity.Comment;
import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ("comment_like_id"))
    private Long commentLikeId;

    // 어느 comment에 좋아요를 할지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    // 누가 좋어요를 했는지  user 필요?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 어느 포스트의 코멘트인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;


//    public CommentLike() {
//    } // 이거 없으면 public class CommentLike 에서 오류
//    // @NoArgsConstructor 넣으면 해결 어느 방법이 좋을지?

    public CommentLike(Comment comment , User user , Post post) {
        this.comment = comment;
        this.user = user;
        this.post = post;
    }


}
