package com.sparta.newsfeed14thfriday.exception;

public class DuplicateNameException extends RuntimeException {

    public DuplicateNameException() {
        super("중복된 유저네임 입니다.");
    }
}
