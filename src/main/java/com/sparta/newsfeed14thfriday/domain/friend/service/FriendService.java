package com.sparta.newsfeed14thfriday.domain.friend.service;

import com.sparta.newsfeed14thfriday.domain.auth.exception.AuthException;
import com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList.FriendListRequestDto;
import com.sparta.newsfeed14thfriday.domain.friend.dto.FriendList.FriendListResponseDto;
import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.domain.friend.repository.FriendRepository;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import com.sparta.newsfeed14thfriday.domain.user.repository.UserRepository;
import com.sparta.newsfeed14thfriday.global.config.TokenUserEmail;
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
    public Friend saveFriend(String token,FriendListRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일: " + requestDto.getUserEmail()));
        User friend = userRepository.findByEmail(requestDto.getFriendEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구이메일: " + requestDto.getFriendEmail()));

        if(!requestDto.getUserEmail().equals(token)){
            throw new AuthException("권한이 없습니다");
        }

        // 중복된 친구 관계가 존재하는지 확인
        friendRepository.findByUserAndFriend(user, friend).ifPresent(existingFriend -> {
            throw new IllegalArgumentException("이미 친구 관계 " + requestDto.getUserEmail() + "와 " + requestDto.getFriendEmail());
        });

        Friend newFriend1 = new Friend(user, friend);
        Friend newFriend2 = new Friend(friend, user);

        // 보낸 사람의 invite를 SEND로, 받은 사람의 invite를 RECEIVE로 설정
        newFriend1.send();
        newFriend2.receive();

        friendRepository.save(newFriend1);
        friendRepository.save(newFriend2);
        return newFriend1;
    }

    // 친구 리스트 조회
    public List<FriendListResponseDto> getFriendList(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일: " + userEmail));

        List<Friend> friendList = friendRepository.findByUserAndStatus(user,"ACCEPTED");
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
    public FriendListResponseDto getFriendByEmail(String userEmail, String friendEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 이메일: " + userEmail));
        User friend = userRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구 이메일: " + friendEmail));

        Friend friendRequest = friendRepository.findByUserAndFriend(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("친구 요청이 존재하지 않습니다: " + userEmail + "와 " + friendEmail));

        return new FriendListResponseDto(
                friendRequest.getId(),
                friendRequest.getFriend().getEmail()
        );
    }

    // 친구 요청 수락
    public void acceptFriendRequest(String token,String userEmail, String friendEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 이메일: " + userEmail));
        User friend = userRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구 이메일: " + friendEmail));
        Friend friendRequest = friendRepository.findByUserAndFriend(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("친구 요청이 존재하지 않습니다: " + userEmail + "와 " + friendEmail));


        if(!friendRequest.getInvite().equals("RECEIVE")) {
            throw new IllegalArgumentException("요청받은 인원이 아닙니다");
        }

        // 이미 수락된 친구 요청인지 확인

        if(!userEmail.equals(token)){
            throw new AuthException("권한이 없습니다");
        }

        if (friendRequest.isAccepted()) {
            throw new IllegalArgumentException("이미 수락된 친구 요청입니다.");
        }

        friendRequest.accept();
        friendRepository.save(friendRequest);

        // 2. friend -> user 친구 요청 수락
        Friend reciprocalRequest = friendRepository.findByUserAndFriend(friend, user)
                .orElseThrow(() -> new IllegalArgumentException("상대방의 친구 요청이 존재하지 않습니다: " + friendEmail + "와 " + userEmail));

        reciprocalRequest.accept(); // friend -> user 수락
        friendRepository.save(reciprocalRequest);

    }

    // 친구 요청 거절 (데이터베이스에서 삭제)
    public void rejectFriendRequest(String token,String userEmail, String friendEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 이메일: " + userEmail));
        User friend = userRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구 이메일: " + friendEmail));
        if(!userEmail.equals(token)){
            throw new AuthException("권한이 없습니다");
        }
        Friend friendRequest1 = friendRepository.findByUserAndFriend(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("친구 요청이 존재하지 않습니다: " + userEmail + "와 " + friendEmail));
        Friend friendRequest2 = friendRepository.findByUserAndFriend(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("친구 요청이 존재하지 않습니다: " + userEmail + "와 " + friendEmail));

        friendRepository.delete(friendRequest1);
        friendRepository.delete(friendRequest2);// 거절 시 데이터베이스에서 삭제
    }

    // 친구 삭제
    public void deleteFriend(String token,String userEmail, String friendEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 이메일: " + userEmail));
        User friend = userRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구 이메일: " + friendEmail));

        if(!userEmail.equals(token)){
            throw new AuthException("권한이 없습니다");
        }

        Friend friendRequest1 = friendRepository.findByUserAndFriend(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구 관계입니다: " + userEmail + "와 " + friendEmail));
        Friend friendRequest2 = friendRepository.findByUserAndFriend(friend, user)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구 관계입니다: " + userEmail + "와 " + friendEmail));

        friendRepository.delete(friendRequest1);
        friendRepository.delete(friendRequest2);
    }
}
