package com.sparta.newsfeed14thfriday.domain.comment.service;


import com.sparta.newsfeed14thfriday.domain.comment.dto.request.CommentDeleteRequestDto;
import com.sparta.newsfeed14thfriday.domain.comment.dto.request.CommentSaveRequestDto;
import com.sparta.newsfeed14thfriday.domain.comment.dto.request.CommentUpdateRequestDto;
import com.sparta.newsfeed14thfriday.domain.comment.dto.response.CommentDetailResponseDto;
import com.sparta.newsfeed14thfriday.domain.comment.dto.response.CommentSaveResponseDto;
import com.sparta.newsfeed14thfriday.domain.comment.dto.response.CommentUpdateResponseDto;
import com.sparta.newsfeed14thfriday.domain.comment.entity.Comment;
import com.sparta.newsfeed14thfriday.domain.comment.repository.CommentRepository;
import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import com.sparta.newsfeed14thfriday.domain.post.repository.PostRepository;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public CommentSaveResponseDto createComment(Long postId, CommentSaveRequestDto commentSaveRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NullPointerException("Post not found"));

        User email = userRepository.findByEmail(commentSaveRequestDto.getUserEmail())
                .orElseThrow(() -> new NullPointerException("User not found"));

        Comment comment = new Comment(
                commentSaveRequestDto.getContents(),
                email,
                post
        );

        Comment savedComment = commentRepository.save(comment);

        return new CommentSaveResponseDto(
                savedComment.getCommentId(),
                email,
                savedComment.getContents(),
                savedComment.getCommentLikeCount(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt()
                ); // 반환값 메세지 형태로 변경? id 유출보다 나을지도

    }


    // 포스트의 코멘트 전부 확인
    public List<CommentDetailResponseDto> getComments(Long postId) {

        List<Comment> commentList = commentRepository.findByPostIdWithUser(postId);

        List<CommentDetailResponseDto> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {

            // deleted 를 가져와서 true면 "삭제된 댓글" 출력 , false면 댓글 내용 출력
            String contents = comment.getDeleted() ? "삭제된 댓글" : comment.getContents();

            CommentDetailResponseDto dto = new CommentDetailResponseDto(
                    comment.getCommentId(),
                    comment.getEmail(),
                    contents,
                    comment.getCommentLikeCount(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt()
            );
            dtoList.add(dto);
        }

        return dtoList;

    }


    @Transactional
    public CommentUpdateResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto){
        Comment comment = findCommentById(commentId);

        User email = userRepository.findByEmail(commentUpdateRequestDto.getEmail())
                .orElseThrow(()-> new NullPointerException("User not found"));

        // 가독성을 위해서 이름 바꾸는거 생각
        if ((comment.getEmail() == null) || !ObjectUtils.nullSafeEquals(email.getEmail(), comment.getEmail().getEmail())){
            throw new IllegalArgumentException("Email not match");
        }

        comment.update(commentUpdateRequestDto.getContents());

        return new CommentUpdateResponseDto(
                comment.getCommentId(),
                email,
                comment.getContents(),
                comment.getCommentLikeCount(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }


//    @Transactional
//    public void deleteComment(Long commentId){
//        Comment comment = findCommentById(commentId);
//
////        User email = userRepository.findByEmail(commentDeleteRequestDto.getEmail())
////                .orElseThrow(()-> new NullPointerException("User not found"));
////
////        // 가독성을 위해서 이름 바꾸는거 생각
////        if ((comment.getEmail() == null) || !ObjectUtils.nullSafeEquals(email.getEmail(), comment.getEmail().getEmail())){
////            throw new IllegalArgumentException("Email not match");
////        }
//
//        commentRepository.deleteById(commentId);
//
//    } // 아예 삭제하는 버전


    @Transactional
    public void deleteCommentUpdate(Long commentId, CommentDeleteRequestDto commentDeleteRequestDto){
        Comment comment = findCommentById(commentId);

        User email = userRepository.findByEmail(commentDeleteRequestDto.getEmail())
                .orElseThrow(()-> new NullPointerException("User not found"));

        // 가독성을 위해서 이름 바꾸는거 생각
        if ((comment.getEmail() == null) || !ObjectUtils.nullSafeEquals(email.getEmail(), comment.getEmail().getEmail())){
            throw new IllegalArgumentException("Email not match");
        }

        comment.deleteComment();

    }

    public Comment findCommentById(Long commentId){
        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new NullPointerException("Comment not found"));

        return comment;
    }






}
