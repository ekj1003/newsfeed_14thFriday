package com.sparta.newsfeed14thfriday.domain.user.service;


import com.sparta.newsfeed14thfriday.domain.auth.exception.AuthException;
import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import com.sparta.newsfeed14thfriday.domain.post.repository.PostRepository;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserChangePwdRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserDeleteRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserProfileUpdateRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserStatusMessageRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.response.UserGetPostsResponseDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.response.UserProfileResponseDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.response.UserProfileUpdateResponseDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.response.UserStatusMessageResponseDto;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import com.sparta.newsfeed14thfriday.exception.AlreadyDeletedUserException;
import com.sparta.newsfeed14thfriday.exception.DeletedUserIdException;
import com.sparta.newsfeed14thfriday.exception.DuplicateNameException;
import com.sparta.newsfeed14thfriday.exception.EmailNotFoundException;

import com.sparta.newsfeed14thfriday.global.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;

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
        if (user.isDeleted()) {
            return true;
        }
        return false;
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
        if (user.isDeleted()) {
            throw new DeletedUserIdException();
        }
        return user;
    }

    @Transactional
    public void deleteUser(String userEmail, UserDeleteRequestDto requestDto) {
        //유저찾기
        User user = findUserByEmail(userEmail);
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }
        if (user.isDeleted()) {
            throw new AlreadyDeletedUserException();
        }
        //user의 deleted값을 true로 변경한다.
        user.deleteUser();


    }
    @Transactional
    public void changePwd(String userEmail, UserChangePwdRequestDto requestDto) {
        User user = findUserByEmail(userEmail);
        //옛날비밀번호와 현비밀번호가 맞으면 새로운비밀번호로 변경
        if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
            throw new AuthException("현재 비밀번호와 유저의 비밀번호가 다릅니다.");
        }
        if (passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword())) {
            throw new AuthException("변경하려는 비밀번호는 현재 사용하는 비밀번호입니다.");
        }

        String password = passwordEncoder.encode(requestDto.getNewPassword());
        user.updatePassword(password);
    }
    //특정유저의 포스트 페이징처리 전체조회.
    public Page<UserGetPostsResponseDto> getUserPosts(int page,int size,String userEmail) {
        User user = findUserByEmail(userEmail);
        String name = user.getUsername();
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Post> posts = postRepository.findByWriterOrderByUpdatedAtDesc(name,pageable);

        return posts.map(post -> new UserGetPostsResponseDto(post));
    }
}
