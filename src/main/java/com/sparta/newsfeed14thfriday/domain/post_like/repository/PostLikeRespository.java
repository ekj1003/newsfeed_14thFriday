package com.sparta.newsfeed14thfriday.domain.post_like.repository;

import com.sparta.newsfeed14thfriday.domain.post_like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeRespository extends JpaRepository<PostLike, Long> {
    @Query("SELECT pl FROM PostLike pl WHERE pl.user.email = :email AND pl.post.postId = :postId")
    Optional<PostLike> findByEmailAndPostId(@Param("email") String email, @Param("postId") Long postId);

    Long countByPost_PostId(Long postId);
}
