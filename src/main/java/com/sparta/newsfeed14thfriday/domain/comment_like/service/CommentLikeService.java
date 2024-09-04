package com.sparta.newsfeed14thfriday.domain.comment_like.service;

import com.sparta.newsfeed14thfriday.domain.comment.entity.Comment;
import com.sparta.newsfeed14thfriday.domain.comment.repository.CommentRepository;
import com.sparta.newsfeed14thfriday.domain.comment_like.dto.request.CommentLikeRequestDto;
import com.sparta.newsfeed14thfriday.domain.comment_like.dto.response.CommentLikeResponseDto;
import com.sparta.newsfeed14thfriday.domain.comment_like.entity.CommentLike;
import com.sparta.newsfeed14thfriday.domain.comment_like.repository.CommentLikeRepository;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    // 누가 어느 댓글에 좋아요를 했는지   동일인이 좋아요를 여러번 하는거 방지
    public CommentLikeResponseDto createCommentLike(Long commentId , String userEmail , CommentLikeRequestDto commentLikeRequestDto) {

        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(()-> new NullPointerException("comment not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new NullPointerException("user not found"));

        // 이미 좋아요 한 유저인지 확인
        if (commentLikeRepository.findByCommentAndUser(comment,user).isPresent()){
            throw new IllegalArgumentException("already like");
        }

        // 확인 필요
        CommentLike commentLike = new CommentLike(comment , user);

        // 좋아요 증가
        comment.updateCommentLikeCount();

        CommentLike savedCommentLike = commentLikeRepository.save(commentLike);

        // 확인 필요
        return new CommentLikeResponseDto(savedCommentLike.getCommentLikeId());

    }

}
