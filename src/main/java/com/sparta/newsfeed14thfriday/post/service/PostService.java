package com.sparta.newsfeed14thfriday.post.service;

import com.sparta.newsfeed14thfriday.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

}
