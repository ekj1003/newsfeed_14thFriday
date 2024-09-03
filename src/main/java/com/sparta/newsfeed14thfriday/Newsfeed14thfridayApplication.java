package com.sparta.newsfeed14thfriday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Newsfeed14thfridayApplication {

    public static void main(String[] args) {
        SpringApplication.run(Newsfeed14thfridayApplication.class, args);
    }

}
