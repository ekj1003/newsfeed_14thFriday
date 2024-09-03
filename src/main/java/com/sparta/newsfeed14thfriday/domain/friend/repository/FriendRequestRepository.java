package com.sparta.newsfeed14thfriday.domain.friend.repository;

import com.sparta.newsfeed14thfriday.domain.friend.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
}
