package com.sparta.newsfeed14thfriday.exception;

public class DeletedUserIdException extends RuntimeException {

    public DeletedUserIdException() {
        super("탈퇴한 유저 Id입니다.");
    }
}
