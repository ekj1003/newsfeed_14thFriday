package com.sparta.newsfeed14thfriday.domain.post.service;

import com.sparta.newsfeed14thfriday.domain.post.dto.request.PostDeleteRequestDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.request.PostSaveRequestDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.request.PostUpdateRequestDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostDetailResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostSaveResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostSimpleResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.dto.response.PostUpdateResponseDto;
import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import com.sparta.newsfeed14thfriday.domain.post.repository.PostRepository;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import com.sparta.newsfeed14thfriday.exception.DeletedUserIdException;
import com.sparta.newsfeed14thfriday.exception.EmailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostSaveResponseDto createPost(PostSaveRequestDto data) {
        // 조회: 유저 존재 여부
        User user = userRepository.findByEmail(data.getEmail())
            .orElseThrow(() -> new NullPointerException("User not found"));


        // 생성: Post Entity
        // 포스트를 생성할때 유저네임을 생성할때 넣는것으로 변경 -> 게시물을 찾을때 유저네임으로 찾으려고
        Post newPost = Post.createNewPost(
            data.getTitle(),
            data.getContents(),
            user
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
            post.getCreatedAt(),
            post.getUpdatedAt(),
                post.getUser().getEmail()


        );
    }

    @Transactional
    public PostUpdateResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        // 조회: 게시물 존재 여부, 유저 존재 여부
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new NullPointerException("Post not found."));
        User user = userRepository.findByEmail(postUpdateRequestDto.getEmail()).orElseThrow(() -> new NullPointerException("User not found."));

        //작성자 일치 여부
        if(post.getUser() == null || !ObjectUtils.nullSafeEquals(user.getEmail(), post.getUser().getEmail())){
            throw new IllegalArgumentException("작성자가 일치하지않습니다.");
        } else{
            // update
            post.update(
                postUpdateRequestDto.getTitle(),
                postUpdateRequestDto.getContents()
            );
        }
        
        // 응답 반환
        return new PostUpdateResponseDto(
            "updated",
            201,
            post.getPostId()
        );
    }

    @Transactional
    public void deletePost(Long postId, PostDeleteRequestDto postDeleteRequestDto) {
        // 조회: postId, userId
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new NullPointerException("Post not found."));
        User user = userRepository.findByEmail(postDeleteRequestDto.getEmail()).orElseThrow(() -> new NullPointerException("User not found."));

        //작성자 일치 여부
        if(!ObjectUtils.nullSafeEquals(user.getEmail(), post.getUser().getEmail())){
            throw new IllegalArgumentException("작성자가 일치하지않습니다.");
        } else{
            // delete
            postRepository.deleteByPostId(postId);
        }
        //postRepository.deleteByPostId(postId);
    }


    public Page<PostSimpleResponseDto> getPosts(int page, int size, String userEmail) {
        User user = findUserByEmail(userEmail);
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Post> posts = postRepository.findByUser_EmailOrderByUpdatedAtDesc(user.getEmail(),pageable);


        return posts.map(post -> new PostSimpleResponseDto(post));
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
        if (user.isDeleted()) {
            throw new DeletedUserIdException();
        }
        return user;
    }
}
