package com.goblin.goandblinblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class GoAndBlinBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoAndBlinBlogApplication.class, args);
    }

}
