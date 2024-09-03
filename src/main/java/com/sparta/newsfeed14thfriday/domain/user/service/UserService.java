package com.sparta.newsfeed14thfriday.domain.user.service;

import com.sparta.newsfeed14thfriday.domain.auth.dto.request.LoginRequestDto;
import com.sparta.newsfeed14thfriday.domain.auth.dto.request.SignupRequestDto;
import com.sparta.newsfeed14thfriday.domain.auth.dto.response.SignupResponseDto;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import com.sparta.newsfeed14thfriday.global.config.PasswordEncoder;
import com.sparta.newsfeed14thfriday.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
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
