package com.sparta.newsfeed14thfriday.domain.post_like.service;

import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import com.sparta.newsfeed14thfriday.domain.post.repository.PostRepository;
import com.sparta.newsfeed14thfriday.domain.post_like.dto.request.PostLikeCreateRequestDto;
import com.sparta.newsfeed14thfriday.domain.post_like.dto.response.PostLikeCreateResponseDto;
import com.sparta.newsfeed14thfriday.domain.post_like.entity.PostLike;
import com.sparta.newsfeed14thfriday.domain.post_like.repository.PostLikeRespository;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRespository postLikeRespository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public PostLikeCreateResponseDto createPostLike(Long postId, PostLikeCreateRequestDto postLikeCreateRequestDto){
        // 조회: 유저 존재 여부, 게시물 존재 여부
        User user = userRepository.findByEmailAndDeleted(postLikeCreateRequestDto.getEmail(),false)
                .orElseThrow(() -> new NullPointerException("User not found"));
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new NullPointerException("Post not found"));
        // 이미 있는지
        if(postLikeRespository.findByEmailAndPostId(postLikeCreateRequestDto.getEmail(), postId).isPresent()){
            throw new RuntimeException("좋아요를 이미 누르셨습니다.");
        }

        // 생성: Post Entity
        PostLike postLike = PostLike.createPostLike(
                post,
                user
        );

        // 저장: PostLike
        PostLike savedPostLike = postLikeRespository.save(postLike);
        updateCountPostLike(postId);

        // 응답 반환
        return new PostLikeCreateResponseDto(
                "created",
                201,
                savedPostLike.getPostLikeId()
        );
    }
    public Long deletePostLike(Long postId, PostLikeCreateRequestDto postLikeCreateRequestDto) {
        // 조회: 유저 존재 여부, 게시물 존재 여부
        User user = userRepository.findByEmailAndDeleted(postLikeCreateRequestDto.getEmail(),false)
                .orElseThrow(() -> new NullPointerException("User not found"));
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new NullPointerException("Post not found"));
        // 이미 있는지
        if(!postLikeRespository.findByEmailAndPostId(postLikeCreateRequestDto.getEmail(), postId).isPresent()){
            throw new RuntimeException("좋아요를 누른적이없습니다.");
        }

        // 삭제: PostLike
        postLikeRespository.deleteById(postId);
        updateCountPostLike(postId);

        // 응답 반환
        return postId;

    }

    public void updateCountPostLike(Long postId){
        Post post = postRepository.findByPostId(postId).orElseThrow(() ->
                new NullPointerException("Post not found."));

        Long count = postLikeRespository.countByPost_PostId(postId);
        post.updateCount(count);
        postRepository.save(post);
    }
}