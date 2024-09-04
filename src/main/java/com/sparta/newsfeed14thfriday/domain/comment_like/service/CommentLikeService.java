package com.sparta.newsfeed14thfriday.domain.comment_like.service;

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


    public void createCommentLike(Long commentId , Long userId) {



    }

}
