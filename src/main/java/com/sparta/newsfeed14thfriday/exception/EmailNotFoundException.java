package com.sparta.newsfeed14thfriday.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
        super("이메일이 없습니다!");
    }
}
