package com.sparta.newsfeed14thfriday.domain.comment_like.service;

import com.sparta.newsfeed14thfriday.domain.comment.entity.Comment;
import com.sparta.newsfeed14thfriday.domain.comment.repository.CommentRepository;
import com.sparta.newsfeed14thfriday.domain.comment_like.dto.request.CommentLikeRequestDto;
import com.sparta.newsfeed14thfriday.domain.comment_like.dto.response.CommentLikeResponseDto;
import com.sparta.newsfeed14thfriday.domain.comment_like.entity.CommentLike;
import com.sparta.newsfeed14thfriday.domain.comment_like.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;


    public CommentLikeResponseDto createCommentLike(Long commentId , CommentLikeRequestDto commentLikeRequestDto) {

        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(()-> new NullPointerException("comment not found"));

        // 확인 필요
        CommentLike commentLike = new CommentLike();

        CommentLike savedCommentLike = commentLikeRepository.save(commentLike);

        // 확인 필요
        return new CommentLikeResponseDto(savedCommentLike.getCommentLikeId());

    }

}
