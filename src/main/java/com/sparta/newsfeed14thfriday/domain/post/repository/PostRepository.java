package com.sparta.newsfeed14thfriday.domain.post.repository;

import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
