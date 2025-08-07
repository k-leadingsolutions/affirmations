package org.affirmations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AffirmationsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AffirmationsApplication.class, args);
    }
}