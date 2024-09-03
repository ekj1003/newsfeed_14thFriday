package com.sparta.newsfeed14thfriday.domain.post_like.service;

import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import com.sparta.newsfeed14thfriday.domain.post.repository.PostRepository;
import com.sparta.newsfeed14thfriday.domain.post_like.dto.request.PostLikeCreateRequestDto;
import com.sparta.newsfeed14thfriday.domain.post_like.dto.response.PostLikeCreateResponseDto;
import com.sparta.newsfeed14thfriday.domain.post_like.entity.PostLike;
import com.sparta.newsfeed14thfriday.domain.post_like.repository.PostLikeRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRespository postLikeRespository;
    private final PostRepository postRepository;
    //private final UserRepository userRepository;

    public PostLikeCreateResponseDto createPostLike(Long postId, PostLikeCreateRequestDto postLikeCreateRequestDto) {
        // 조회: 유저 존재 여부, 게시물 존재 여부
//        Optional<Users> foundUserOptional = userRepository.findById(data.getUserId())
//            .orElseThrow(() -> new NullPointerException("User not found"));
//        User foundUser = foundUserOptional.get();
        Post post = postRepository.findByPostId(postId)
            .orElseThrow(() -> new NullPointerException("Post not found"));

        // 이미 좋아요되어있으면 에러 반환
//        if(postLikeRespository.findByUserAndPost(user, post).isPresent()){
//            throw new Exception();
//        }
        
        // 생성: Post Entity
        PostLike postLike = PostLike.createPostLike(
            post
        );

        // 저장: PostLike
        PostLike savedPostLike = postLikeRespository.save(postLike);
        post.updatePostLikeCount();

        // 응답 반환
        return new PostLikeCreateResponseDto(
            "created",
            201,
            savedPostLike.getPostLikeId()
        );

    }
}
