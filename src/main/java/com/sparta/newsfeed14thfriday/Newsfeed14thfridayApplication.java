package com.sparta.newsfeed14thfriday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Newsfeed14thfridayApplication {

    public static void main(String[] args) {
        SpringApplication.run(Newsfeed14thfridayApplication.class, args);
    }

}
