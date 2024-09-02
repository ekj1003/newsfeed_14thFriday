package com.sparta.newsfeed14thfriday.domain.user.entity;

import com.sparta.newsfeed14thfriday.entity_common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Users extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false,length = 50)
    private String username;

    @Column(nullable = false,length = 50)
    private String email;

    @Column(nullable = false,length = 200)
    private String password;

    //기본값은 false true 상태라면 삭제된 상태입니다.
    private boolean deleted = false;

    private Long friendsCount;
}
