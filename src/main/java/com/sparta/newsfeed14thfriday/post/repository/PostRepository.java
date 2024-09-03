package com.sparta.newsfeed14thfriday.post.repository;

import com.sparta.newsfeed14thfriday.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostId(Long postId);

    void deleteByPostId(Long postId);
}
