package com.sparta.newsfeed14thfriday.domain.friend.repository;

import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FriendRepository extends JpaRepository<Friend, Integer> {
}
