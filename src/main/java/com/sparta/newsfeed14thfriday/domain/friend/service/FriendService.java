package com.sparta.newsfeed14thfriday.domain.friend.service;

import com.sparta.newsfeed14thfriday.domain.friend.dto.FriendListResponseDto;
import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.domain.friend.repository.FriendRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    private final FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    //친구 리스트 저장
    public List<FriendListResponseDto> getFriend() {
        List<Friend> friendList = friendRepository.findAll();

        List<FriendListResponseDto> dtoList = new ArrayList<>();
        for (Friend friend : friendList) {
            dtoList.add(new FriendListResponseDto(
                friend.getFriend_id(),
                friend.getUsersFriend()
            ));
        }
        return dtoList;
    }



}
