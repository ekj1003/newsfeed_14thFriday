package com.sparta.newsfeed14thfriday.post.service;

import com.sparta.newsfeed14thfriday.post.dto.request.PostSaveRequestDto;
import com.sparta.newsfeed14thfriday.post.dto.response.PostDetailResponseDto;
import com.sparta.newsfeed14thfriday.post.dto.response.PostSaveResponseDto;
import com.sparta.newsfeed14thfriday.post.entity.Post;
import com.sparta.newsfeed14thfriday.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
