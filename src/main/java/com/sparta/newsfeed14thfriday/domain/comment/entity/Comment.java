package com.sparta.newsfeed14thfriday.domain.comment.entity;


import com.sparta.newsfeed14thfriday.domain.comment.dto.CommentRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
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

    //기본값 false    true 상태라면 삭제된 상태
    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id")
//    private Post post;



//    public Comment(String contents , User user , long commentLikeCount){
//        this.contents = contents;
//        this.commentLikeCount = commentLikeCount;
//        this.user = user;
//    }

    public Comment(CommentRequestDto requestDto){
        this.contents = requestDto.getContents();
        this.commentLikeCount = requestDto.getCommentLikeCount();

    }

    public void update(String contents , long  commentLikeCount){
        this.contents = contents;
        this.commentLikeCount = commentLikeCount;
    }

}
