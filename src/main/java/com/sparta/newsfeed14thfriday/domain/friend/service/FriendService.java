package com.sparta.newsfeed14thfriday.domain.friend.service;

import com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList.FriendListRequestDto;
import com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList.FriendListResponseDto;
import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.domain.friend.repository.FriendRepository;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    // 친구 저장
    public Friend saveFriend(FriendListRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + requestDto.getUserEmail()));
        User friend = userRepository.findById(requestDto.getFriendEmail())
                .orElseThrow(() -> new IllegalArgumentException("Friend not found: " + requestDto.getFriendEmail()));

        Friend newFriend = new Friend(user, friend);
        return friendRepository.save(newFriend);
    }

    // 친구 리스트 조회
    public List<FriendListResponseDto> getFriendList(String userEmail) {
        User user = userRepository.findById(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userEmail));

        List<Friend> friendList = friendRepository.findByUser(user);
        List<FriendListResponseDto> dtoList = new ArrayList<>();

        for (Friend friend : friendList) {
            dtoList.add(new FriendListResponseDto(
                    friend.getId(),
                    friend.getFriend().getEmail() // User 객체에서 이메일 추출
            ));
        }
        return dtoList;
    }

    // 특정 친구 조회
    public FriendListResponseDto getFriendById(Long friendId) {
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found: " + friendId));
        return new FriendListResponseDto(
                friend.getId(),
                friend.getFriend().getEmail() // User 객체에서 이메일 추출
        );
    }

}
