package com.sparta.newsfeed14thfriday.domain.comment_like.entity;

import com.sparta.newsfeed14thfriday.domain.comment.entity.Comment;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ("comment_like_id"))
    private Long commentLikeId;

    // 어느 comment에 좋아요를 할지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment commentId;

    // 누가 좋어요를 했는지  user 필요?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;


    public CommentLike() {

    }


}
