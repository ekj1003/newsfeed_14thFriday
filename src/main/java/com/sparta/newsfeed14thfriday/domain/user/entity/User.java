package com.sparta.newsfeed14thfriday.domain.user.entity;

import com.sparta.newsfeed14thfriday.domain.friend.entity.Friend;
import com.sparta.newsfeed14thfriday.entity_common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class User extends Timestamped {


    @Id
    @Column(nullable = false,length = 50,unique = true)
    private String email;

    @Column(nullable = false,length = 50)
    private String username;

    @Column(nullable = false,length = 200)
    private String password;

    //기본값은 false true 상태라면 삭제된 상태입니다.
    private boolean deleted = false;

    private Long friendsCount;

    @OneToMany(mappedBy = "UsersFriend")
    private List<Friend> friends = new ArrayList<>();
}
