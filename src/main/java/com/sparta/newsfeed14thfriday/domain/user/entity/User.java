package com.sparta.newsfeed14thfriday.domain.user.entity;

import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.entity_common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @Column(nullable = false,length = 50,unique = true)
    private String email;

    @Column(length = 50)
    private String statusMessage;

    @Column(nullable = false,length = 50)
    private String username;

    @Column(nullable = false,length = 200)
    private String password;

    //기본값은 false true 상태라면 삭제된 상태입니다.
    private boolean deleted = false;

    private Long friendsCount;

    @OneToMany(mappedBy = "UsersFriend")
    private List<Friend> friends = new ArrayList<>();

    public User(String username, String password, String email) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public void updateUserName(String username) {
        this.username = username;
    }
    public void updateStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
