package com.sparta.newsfeed14thfriday.post.service;

import com.sparta.newsfeed14thfriday.post.dto.request.PostSaveRequestDto;
import com.sparta.newsfeed14thfriday.post.dto.request.PostUpdateRequestDto;
import com.sparta.newsfeed14thfriday.post.dto.response.PostDetailResponseDto;
import com.sparta.newsfeed14thfriday.post.dto.response.PostSaveResponseDto;
import com.sparta.newsfeed14thfriday.post.dto.response.PostUpdateResponseDto;
import com.sparta.newsfeed14thfriday.post.entity.Post;
import com.sparta.newsfeed14thfriday.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
//    private final UserRepository userRepository;

    public PostSaveResponseDto createPost(PostSaveRequestDto data) {
        // 조회: 유저 존재 여부
//        Optional<Users> foundUserOptional = userRepository.findById(data.getUserId())
//            .orElseThrow(() -> new NullPointerException("User not found"));
//        User foundUser = foundUserOptional.get();

        // 생성: Post Entity
        Post newPost = Post.createNewPost(
            //foundUser,
            data.getTitle(),
            data.getContents()
        );

        // 저장: Post
        Post savedPost = postRepository.save(newPost);

        // 응답 반환
        return new PostSaveResponseDto(
            "created",
            201,
            savedPost.getPostId()
        );
    }

    public PostDetailResponseDto getPost(Long postId) {
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new NullPointerException("Post not found."));

        return new PostDetailResponseDto(
            post.getPostId(),
            post.getTitle(),
            post.getContents(),
            post.getCommentCount(),
            post.getPostLikeCount(),
            post.getCreateAt(),
            post.getModifiedAt()
        );
    }

    @Transactional
    public PostUpdateResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        // 조회: 게시물 존재 여부, 유저 존재 여부
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new NullPointerException("Post not found."));
//        User user = userRepository.findById(data.getUserId()).orElseThrow(() -> new NullPointerException("User not found."));
//
//        //작성자 일치 여부
//        if(post.getUser() == null || !ObjectUtils.nullSafeEquals(user.getId(), post.getUser.getId())){
//            throw new IllegalArgumentException("작성자가 일치하지않습니다.");
//        } else{
//            // update
//            post.update(
//                data.getTitle(),
//                data.getContents()
//            );
//        }
        // update
        post.update(
            postUpdateRequestDto.getTitle(),
            postUpdateRequestDto.getContents()
        );
        
        // 응답 반환
        return new PostUpdateResponseDto(
            "updated",
            201,
            post.getPostId()
        );
    }
}
