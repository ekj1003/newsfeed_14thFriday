package com.sparta.newsfeed14thfriday.domain.auth.service;

import com.sparta.newsfeed14thfriday.domain.auth.dto.request.LoginRequestDto;
import com.sparta.newsfeed14thfriday.domain.auth.dto.request.SignupRequestDto;
import com.sparta.newsfeed14thfriday.domain.auth.dto.response.LoginResponseDto;
import com.sparta.newsfeed14thfriday.domain.auth.dto.response.SignupResponseDto;
import com.sparta.newsfeed14thfriday.domain.auth.exception.AuthException;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import com.sparta.newsfeed14thfriday.global.config.PasswordEncoder;
import com.sparta.newsfeed14thfriday.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        User user = new User(username, password, email);

        User savedUser = userRepository.save(user);

        String bearerToken = jwtUtil.createToken(
                savedUser.getUsername(),
                savedUser.getEmail()
        );

        return new SignupResponseDto(bearerToken);
    }


    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new AuthException("가입되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }

        String bearerToken = jwtUtil.createToken(
                user.getUsername(),
                user.getEmail()
        );

        return new LoginResponseDto(bearerToken);
    }
}
