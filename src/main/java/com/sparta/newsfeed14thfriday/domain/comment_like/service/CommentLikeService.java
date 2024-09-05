package com.sparta.newsfeed14thfriday.domain.comment_like.service;

import com.sparta.newsfeed14thfriday.domain.auth.exception.AuthException;
import com.sparta.newsfeed14thfriday.domain.comment.entity.Comment;
import com.sparta.newsfeed14thfriday.domain.comment.repository.CommentRepository;
import com.sparta.newsfeed14thfriday.domain.comment_like.dto.request.CommentLikeRequestDto;
import com.sparta.newsfeed14thfriday.domain.comment_like.dto.response.CommentLikeResponseDto;
import com.sparta.newsfeed14thfriday.domain.comment_like.entity.CommentLike;
import com.sparta.newsfeed14thfriday.domain.comment_like.repository.CommentLikeRepository;
import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import com.sparta.newsfeed14thfriday.domain.post.repository.PostRepository;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    // 누가 어느 포스트의 어느 댓글에 좋아요를 했는지   동일인이 좋아요를 여러번 하는거 방지
    public CommentLikeResponseDto createCommentLike(String email,Long commentId , Long postId, CommentLikeRequestDto commentLikeRequestDto) {

        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(()-> new NullPointerException("comment not found"));

        User user = userRepository.findByEmail(commentLikeRequestDto.getUserEmail())
                .orElseThrow(()-> new NullPointerException("user not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new NullPointerException("post not found"));
        //token에서 받아온 이메일과 request에 있는 이메일이 다르다= 본인이 다른아이디인척 좋아요누른다.
        if(!commentLikeRequestDto.getUserEmail().equals(email)){
            throw new AuthException("권한이 없습니다");
        }

        // 이미 좋아요 한 유저인지 확인
        if (commentLikeRepository.findByCommentAndUserAndPost(comment,user,post).isPresent()){
            throw new IllegalArgumentException("already liked");
        }

        // 확인 필요
        CommentLike commentLike = new CommentLike(comment , user , post);

        // 좋아요 증가
        comment.updateCommentLikeCount();

        CommentLike savedCommentLike = commentLikeRepository.save(commentLike);

        // 확인 필요
        return new CommentLikeResponseDto(savedCommentLike.getCommentLikeId());

    }


    // 좋아요 취소
    @Transactional
    public void deleteCommentLike(String email,Long commentId, Long postId, CommentLikeRequestDto commentLikeRequestDto) {

        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(()-> new NullPointerException("comment not found"));

        User user = userRepository.findByEmail(commentLikeRequestDto.getUserEmail())
                .orElseThrow(()-> new NullPointerException("user not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new NullPointerException("post not found"));
        //token에서 받아온 이메일과 request에 있는 이메일이 다르다= 본인이 다른아이디인척 좋아요를 취소한다.
        if(!commentLikeRequestDto.getUserEmail().equals(email)){
            throw new AuthException("권한이 없습니다");
        }

        // 이미 좋아요 한 유저인지 확인
        if (commentLikeRepository.findByCommentAndUserAndPost(comment,user,post).isPresent()){

            commentLikeRepository.delete(commentLikeRepository.findByCommentAndUserAndPost(comment,user,post).get());

            comment.deleteCommentLikeCount();

        }

        commentRepository.save(comment);

    }

}
