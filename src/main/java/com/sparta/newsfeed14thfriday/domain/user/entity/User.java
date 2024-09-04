package com.sparta.newsfeed14thfriday.domain.user.entity;

import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.domain.post.entity.Post;
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
    //이름,이메일은 중복이 불가하도록 수정
    @Column(nullable = false,length = 50,unique = true)
    private String username;

    @Column(nullable = false,length = 200)
    private String password;

    //기본값은 false true 상태라면 삭제된 상태입니다.
    private boolean deleted = false;

    private Long friendsCount = 0L; // 친구 수를 0으로 초기화

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
    public void deleteUser() {
        this.deleted = true;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void restoreUser() {
        this.deleted = false;
    }
}
