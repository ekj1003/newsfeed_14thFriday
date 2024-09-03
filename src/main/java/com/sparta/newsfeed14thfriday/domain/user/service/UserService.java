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

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
}
