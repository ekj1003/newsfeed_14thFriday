package com.sparta.newsfeed14thfriday.domain.friend.repository;

import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findById(Long friendId);
    List<Friend> findByUserAndStatus(User user,String accept);
    Optional<Friend> findByUserAndFriend(User user, User friend); //중복체크

    List<Friend> findAllByUser(User user);
}
