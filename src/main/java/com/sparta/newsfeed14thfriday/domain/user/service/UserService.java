package com.sparta.newsfeed14thfriday.domain.user.service;


import com.sparta.newsfeed14thfriday.domain.user.dto.*;

import com.sparta.newsfeed14thfriday.domain.user.dto.LoginRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.SignupRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.SignupResponseDto;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import com.sparta.newsfeed14thfriday.exception.DuplicateEmailException;
import com.sparta.newsfeed14thfriday.exception.EmailNotFoundException;
import com.sparta.newsfeed14thfriday.global.config.PasswordEncoder;
import com.sparta.newsfeed14thfriday.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원 가입 구현
    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername(); // username 받아오기

        // 이메일 형식 확인
        if (!requestDto.isEmailValid()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }

        // 비밀번호 형식 확인
        if (!requestDto.isPasswordValid()) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상이어야 하며, 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다.");
        }

        String password = passwordEncoder.encode(requestDto.getPassword()); // 받아온 password 암호화해서 넣기

        // email 중복확인, email: unique=true
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            //이메일 중복시 이메일 중복 예외처리
            throw new DuplicateEmailException();
        }

        User user = new User(username, password, email);
        userRepository.save(user);
        return new SignupResponseDto(user);
    }
    public UserProfileResponseDto getProfile(String userEmail) {
        User user = findUserByEmail(userEmail);
        return new UserProfileResponseDto(user);
    }
    @Transactional
    public UserProfileResponseDto updateProfile(String userEmail) {
        return null;
    }
    @Transactional
    public UserStatusMessageResponseDto updateStatusMessage(String userEmail, UserStatusMessageRequestDto requestDto) {
        User user = findUserByEmail(userEmail);
        String newStatusMessage = requestDto.getStatusMessage();
        user.updateStatusMessage(newStatusMessage);

        return new UserStatusMessageResponseDto(newStatusMessage);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
    }

    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getEmail());
        jwtUtil.addJwtToCookie(token, res);
    }

}
