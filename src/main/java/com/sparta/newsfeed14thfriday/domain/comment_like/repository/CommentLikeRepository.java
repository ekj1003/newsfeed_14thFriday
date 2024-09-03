package com.sparta.newsfeed14thfriday.domain.comment_like.repository;

import com.sparta.newsfeed14thfriday.domain.comment_like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

}
