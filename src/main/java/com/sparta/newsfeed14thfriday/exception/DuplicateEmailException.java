package com.sparta.newsfeed14thfriday.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException() {
        super("이메일이 중복 되었습니다");
    }
}
