package com.sparta.newsfeed14thfriday.domain.comment.service;


import com.sparta.newsfeed14thfriday.domain.comment.dto.CommentRequestDto;
import com.sparta.newsfeed14thfriday.domain.comment.dto.CommentResponseDto;
import com.sparta.newsfeed14thfriday.domain.comment.entity.Comment;
import com.sparta.newsfeed14thfriday.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


//    // service 에서 request , response 직접 다루지 말 것
//    public CommentResponseDto createComment(CommentRequestDto RequestDto) {
//
//    }






}
