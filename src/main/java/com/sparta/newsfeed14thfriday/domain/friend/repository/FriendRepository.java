package com.sparta.newsfeed14thfriday.domain.friend.repository;

import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FriendRepository extends JpaRepository<Friend, Integer> {
    Optional<Friend> findById(Long friendId);
}
