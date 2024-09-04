package com.sparta.newsfeed14thfriday.domain.comment.entity;

import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
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

    private Long commentLikeCount = 0L;

    //기본값 false    true 상태라면 삭제된 상태
    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", nullable = false, unique = true)
    private User email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;



    public Comment(String contents, User email, Post post){
        this.contents = contents;
        this.email = email;
        this.post = post;
    }


    public void update(String contents){
        this.contents = contents;
    }


    public void deleteComment(){
        this.deleted = true;
    }


    public void updateCommentLikeCount(){
        this.commentLikeCount = this.commentLikeCount + 1;
    }


    public void deleteCommentLikeCount(){
        // 0일 경우 음수로 내려갈 위험 있음
        if (this.commentLikeCount > 0){
            this.commentLikeCount = this.commentLikeCount - 1;
        }
    }


}
