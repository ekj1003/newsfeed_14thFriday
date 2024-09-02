package com.sparta.newsfeed14thfriday.domain.comment.repository;


import com.sparta.newsfeed14thfriday.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
