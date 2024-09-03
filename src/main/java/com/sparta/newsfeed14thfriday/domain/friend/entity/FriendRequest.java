package com.sparta.newsfeed14thfriday.domain.friend.entity;

import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long friends_request_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_EMAIL")
    private User UsersFriend;

    @Column
    private boolean friend_state;
}
