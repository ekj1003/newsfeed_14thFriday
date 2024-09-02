package com.sparta.newsfeed14thfriday.post.repository;

import com.sparta.newsfeed14thfriday.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
