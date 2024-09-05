package com.sparta.newsfeed14thfriday.domain.friend.entity;


import com.sparta.newsfeed14thfriday.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Friend {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_email")
    private User friend;

    @Column(name = "status")
    private String status;

    public Friend(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }

    //수락
    public void accept() {
        this.status = "ACCEPTED";
    }
    //거절
    public void reject() {
        this.status = "REJECTED";
    }

}
