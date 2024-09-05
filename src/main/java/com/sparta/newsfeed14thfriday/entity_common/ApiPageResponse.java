package com.sparta.newsfeed14thfriday.entity_common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApiPageResponse <T>{
    private final String message;
    private final Integer statusCode;
    private final Page<T> data;

    public static <T> ApiPageResponse<T> createSuccess(String message,Integer statusCode,Page<T> data) {

        return new ApiPageResponse<>(message,statusCode,data);

    }
    public static <T> ApiResponse<T> createError(String message,Integer statusCode) {
        return new ApiResponse<>(message,statusCode,null);
    }
}
