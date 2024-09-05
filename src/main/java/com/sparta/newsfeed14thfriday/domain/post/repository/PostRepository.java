package com.sparta.newsfeed14thfriday.domain.post.repository;

import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostId(Long postId);

    void deleteByPostId(Long postId);
    Page<Post> findByWriterOrderByModifiedAtDesc(String writer, Pageable pageable);

    Page<Post> findByCreateAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
