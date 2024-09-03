package com.sparta.newsfeed14thfriday.post_like.repository;

import com.sparta.newsfeed14thfriday.post_like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRespository extends JpaRepository<PostLike, Long> {
}
