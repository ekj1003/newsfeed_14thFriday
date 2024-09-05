package com.sparta.newsfeed14thfriday.domain.user.service;


import com.sparta.newsfeed14thfriday.domain.auth.exception.AuthException;
import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.domain.friend.repository.FriendRepository;
import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
import com.sparta.newsfeed14thfriday.domain.post.repository.PostRepository;
import com.sparta.newsfeed14thfriday.domain.user.dto.UserListDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserChangePwdRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserDeleteRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserProfileUpdateRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.request.UserStatusMessageRequestDto;
import com.sparta.newsfeed14thfriday.domain.user.dto.response.*;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import com.sparta.newsfeed14thfriday.exception.DeletedUserIdException;
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
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final FriendRepository friendRepository;

    //유저 단건 조회
    public UserProfileDetailResponseDto getProfile(String tokenEmail, String userEmail) {
        User user = findUserByEmail(userEmail);
        if (!userEmail.equals(tokenEmail)) {
            log.info("유저데이터심플");
            return new UserProfileDetailResponseDto(user);
        } else {
            log.info("유저데이터디테일");
            List<Friend> friendList = friendRepository.findByUserAndStatus(user,"ACCEPTED");
            List<String> friendsEmailsList = friendList.stream().map(friend -> friend.getFriend().getEmail()).toList();
            List<User> UserList = userRepository.findAllByEmailInAndDeleted(friendsEmailsList,false);
            List<UserListDto> userListDtos = new ArrayList<>();
            for (User u : UserList) {
                userListDtos.add(new UserListDto(u));
            }
            return new UserProfileDetailResponseDto(user,userListDtos);
        }
    }

    @Transactional
    //유저 이름변경
    //게시물 수정, 삭제는 작성자 본인만 처리할 수 있습니다. jwt토큰으로 해결하는방법을찾아보자.
    //문제 해결
    //작성자가 아닌 다른 사용자가 게시물 수정 -> @Pathvariable로 받은 PathUserEmail과 토큰값에서 추출한 userEmail이 같을때만 수정할수 있도록 수정했습니다!!
    // , 삭제를 시도하는 경우 예외처리를하자
    public UserProfileUpdateResponseDto updateProfile(String pathUserEmail,String userEmail, UserProfileUpdateRequestDto requestDto) {
        //유저를찾습니다
        User user = findUserByEmail(userEmail);

        //만약 토큰에서 가져온 userEmail과 PathVariable로 받은 유저이메일이 다르면 수정할 수 없습니다.
        if(!pathUserEmail.equals(userEmail)){
            throw new AuthException("권한이 없습니다");
        }
        String newName = requestDto.getUserName();
        user.updateUserName(newName);
        return new UserProfileUpdateResponseDto(newName);
    }

    @Transactional
    //상태메시지 변경
    public UserStatusMessageResponseDto updateStatusMessage(String pathUserEmail,String userEmail, UserStatusMessageRequestDto requestDto) {
        //유저 찾기
        User user = findUserByEmail(userEmail);
        //상태메시지 받아오기
        if(!pathUserEmail.equals(userEmail)){
            throw new AuthException("권한이 없습니다");
        }
        String newStatusMessage = requestDto.getStatusMessage();
        //상태메시지 업데이트
        user.updateStatusMessage(newStatusMessage);
        return new UserStatusMessageResponseDto(newStatusMessage);
    }

    public boolean isDeletedUser(String email) {
        User user = userRepository.findByEmailAndDeleted(email,false).orElseThrow(EmailNotFoundException::new);
        if (user.isDeleted()) {
            return true;
        }
        return false;
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmailAndDeleted(email,false).orElseThrow(EmailNotFoundException::new);
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
        //user의 deleted값을 true로 변경한다.
        user.deleteUser();


    }
    //- 비밀번호 수정 조건
    //    - 비밀번호 수정 시, 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우에만 수정할 수 있습니다.
    //    - 현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.
    //- 비밀번호 수정 시, 본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우(O)
    //- 비밀번호 형식이 올바르지 않은 경우(signupRequestDto 에서 해결)
    //- 현재 비밀번호와 동일한 비밀번호로 수정하는 경우(O)
    @Transactional
    public void changePwd(String userEmail, UserChangePwdRequestDto requestDto) {
        User user = findUserByEmail(userEmail);
        //구비밀번호와 방금입력한 현비밀번호가 맞으면 새로운비밀번호로 변경
        if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
            throw new AuthException("현재 비밀번호와 유저의 비밀번호가 다릅니다.");
        }
        if (passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword())) {
            throw new AuthException("변경하려는 비밀번호는 현재 사용하는 비밀번호입니다.");
        }

        // 최소 8자, 대소문자, 숫자, 특수문자 각각 최소 1개 포함
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        boolean flag = Pattern.matches(passwordRegex, requestDto.getNewPassword());
        if (!flag) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상이어야 하며, 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다.");
        }
        String password = passwordEncoder.encode(requestDto.getNewPassword());
        user.updatePassword(password);
    }
    //특정유저의 포스트 페이징처리 전체조회.
    public Page<UserGetPostsResponseDto> getUserPosts(int page,int size,String userEmail) {
        User user = findUserByEmail(userEmail);
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Post> posts = postRepository.findByUser_EmailAndDeletedFalseOrderByUpdatedAtDesc(user.getEmail(),pageable);


        return posts.map(post -> new UserGetPostsResponseDto(post));
    }

    public Page<UserNewsfeedResponseDto> getNewsfeed(int page,int size,String userEmail, String token) {
        User user = findUserByEmail(userEmail);
        //상태메시지 받아오기
        if(!userEmail.equals(token)){
            throw new AuthException("권한이 없습니다");
        }
        Pageable pageable = PageRequest.of(page-1,size);
        List<Friend> friends = friendRepository.findByUserAndStatus(user,"ACCEPTED");


        List<String> friendsEmailsList = friends.stream().map(friend -> friend.getFriend().getEmail()).toList();
        Page<Post> friendsPost = postRepository.findAllByUser_EmailInAndDeletedFalseOrderByUpdatedAtDesc(friendsEmailsList,pageable);

        return friendsPost.map(post -> new UserNewsfeedResponseDto(post));
    }
}
