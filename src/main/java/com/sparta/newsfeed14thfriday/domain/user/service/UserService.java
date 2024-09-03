package com.sparta.newsfeed14thfriday.domain.user.service;


import com.sparta.newsfeed14thfriday.domain.user.dto.request.LoginRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.SignupRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.response.SignupResponseDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserProfileUpdateRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserStatusMessageRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.response.UserProfileResponseDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.response.UserProfileUpdateResponseDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.response.UserStatusMessageResponseDto;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import com.sparta.newsfeed14thfriday.exception.DeletedUserIdException;
import com.sparta.newsfeed14thfriday.exception.DuplicateEmailException;
import com.sparta.newsfeed14thfriday.exception.DuplicateNameException;
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
        //userName 중복확인, userName:unique=true;
        String name = requestDto.getUsername();
        Optional<User> checkUserName = userRepository.findByUsername(name);
        if (checkUserName.isPresent()) {
            //이름 중복시 이름 중복 예외처리
            throw new DuplicateNameException();
        }

        User user = new User(username, password, email);
        userRepository.save(user);
        return new SignupResponseDto(user);
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

        if (user.isDeleted()) {
            throw new DeletedUserIdException();
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getEmail());
        jwtUtil.addJwtToCookie(token, res);
    }
    //유저 단건 조회
    public UserProfileResponseDto getProfile(String userEmail) {
        User user = findUserByEmail(userEmail);
        return new UserProfileResponseDto(user);
    }
    @Transactional
    //유저 이름변경
    public UserProfileUpdateResponseDto updateProfile(String userEmail, UserProfileUpdateRequestDto requestDto) {
        User user = findUserByEmail(userEmail);
        String newName = requestDto.getUserName();
        //이름중복검사
        Optional<User> checkUserName = userRepository.findByUsername(newName);
        if (checkUserName.isPresent()) {
            //이름 중복시 이름 중복 예외처리
            throw new DuplicateNameException();
        }
        user.updateUserName(newName);
        return new UserProfileUpdateResponseDto(newName);
    }
    @Transactional
    //상태메시지 변경
    public UserStatusMessageResponseDto updateStatusMessage(String userEmail, UserStatusMessageRequestDto requestDto) {
        //유저 찾기
        User user = findUserByEmail(userEmail);
        //상태메시지 받아오기
        String newStatusMessage = requestDto.getStatusMessage();
        //상태메시지 업데이트
        user.updateStatusMessage(newStatusMessage);
        return new UserStatusMessageResponseDto(newStatusMessage);
    }

    public boolean isDeletedUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
        if(user.isDeleted()) {
            return true;
        }
        return false;
    }
    public User findUserByEmail(String email) {
       User user = userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
       if(user.isDeleted()) {
           throw new DeletedUserIdException();
       }
       return user;
    }

    @Transactional
    public void deleteUser(String userEmail) {
        //유저찾기
        User user = findUserByEmail(userEmail);
        //user의 deleted값을 true로 변경한다.
        user.deleteUser();
    }
}
