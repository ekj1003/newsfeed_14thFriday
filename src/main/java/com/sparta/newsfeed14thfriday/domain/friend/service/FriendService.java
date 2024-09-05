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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일: " + requestDto.getUserEmail()));
        User friend = userRepository.findById(requestDto.getFriendEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구이메일: " + requestDto.getFriendEmail()));

        // 중복된 친구 관계가 존재하는지 확인
        friendRepository.findByUserAndFriend(user, friend).ifPresent(existingFriend -> {
            throw new IllegalArgumentException("이미 친구 관계 " + requestDto.getUserEmail()  + requestDto.getFriendEmail());
        });


        Friend newFriend1 = new Friend(user, friend);
        Friend newFriend2 = new Friend(friend, user);
        friendRepository.save(newFriend1);
        friendRepository.save(newFriend2);
        return newFriend1;
    }

    // 친구 리스트 조회
    public List<FriendListResponseDto> getFriendList(String userEmail) {
        User user = userRepository.findById(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("친구가 없습니다 " + userEmail));

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
                .orElseThrow(() -> new IllegalArgumentException("검색하신 친구가 없습니다 " + friendId));
        return new FriendListResponseDto(
                friend.getId(),
                friend.getFriend().getEmail() // User 객체에서 이메일 추출
        );
    }

    // 친구 요청 수락
    public void acceptFriendRequest(Long friendId, String receiverEmail) {
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("친구요청하신 친구가 없습니다: " + friendId));
        // 요청한 사용자가 요청을 받은 사람인지 확인
        if (!friend.getFriend().getEmail().equals(receiverEmail)) {
            throw new IllegalArgumentException("이메일이 틀립니다");
        }

        friend.accept();
        friendRepository.save(friend);
    }

    // 친구 요청 거절 (데이터베이스에서 삭제)
    public void rejectFriendRequest(Long friendId, String receiverEmail) {
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구입니다 " + friendId));

        // 요청한 사용자가 요청을 받은 사람인지 확인
        if (!friend.getFriend().getEmail().equals(receiverEmail)) {
            throw new IllegalArgumentException("이메일이 틀립니다");
        }

        friendRepository.delete(friend);  // 거절 시 데이터베이스에서 삭제
    }

    // 친구 삭제
    public void deleteFriend(Long friendId) {
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구입니다 " + friendId));
        friendRepository.delete(friend);
    }
}
