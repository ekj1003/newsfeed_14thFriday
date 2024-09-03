package com.sparta.newsfeed14thfriday.domain.post.service;

import com.sparta.newsfeed14thfriday.domain.post.dto.request.PostSaveRequestDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostSaveResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import com.sparta.newsfeed14thfriday.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
