package com.sparta.newsfeed14thfriday.domain.comment.repository;


import com.sparta.newsfeed14thfriday.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 다시 공부해 볼 것
    @Query("SELECT c FROM Comment c JOIN FETCH c.email WHERE c.post.postId = :postId")
    List<Comment> findByPostIdWithUser(@Param("postId") Long postId);

    Optional<Comment> findCommentById(Long commentId);

    void deleteCommentById(Long commentId);

}
