package com.sparta.newsfeed14thfriday.domain.comment_like.entity;

import com.sparta.newsfeed14thfriday.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ("comment_like_id"))
    private Long commentLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;


    public CommentLike() {

    }


}
