package com.sparta.newsfeed14thfriday.domain.comment.entity;


import com.sparta.newsfeed14thfriday.entity_common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @Column(name = "comment_like_count",nullable = false,length = 500)
    private Long commentLikeCount;

    @Column(name = "deleted",nullable = false)
    private Boolean deleted;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    private Post post;





}
