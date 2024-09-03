package com.sparta.newsfeed14thfriday.exception;

public class AlreadyDeletedUserException extends RuntimeException {
    public AlreadyDeletedUserException() {
        super("이미 탈퇴한 사용자 입니다");
    }
}
