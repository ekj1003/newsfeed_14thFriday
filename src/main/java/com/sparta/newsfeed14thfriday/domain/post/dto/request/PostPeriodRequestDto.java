package com.sparta.newsfeed14thfriday.domain.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PostPeriodRequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
}
