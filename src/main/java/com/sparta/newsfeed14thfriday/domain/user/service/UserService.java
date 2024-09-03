package com.sparta.newsfeed14thfriday.domain.user.service;

import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import com.sparta.newsfeed14thfriday.global.config.PasswordEncoder;
import com.sparta.newsfeed14thfriday.global.jwt.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

}
