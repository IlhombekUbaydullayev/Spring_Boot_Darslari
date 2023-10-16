package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
        
    }

//    @Scheduled(fixedRate = 1000L)
//    public void startRate() {
//        System.out.println("New Rate " + new Date());
//    }
//
//    @Scheduled(fixedDelay = 1000L)
//    public void startDelay() {
//        System.out.println("New Delay " + new Date());
//    }

    @Scheduled(cron = "0 31 18 * * *")
    public void startCron() {
        System.out.println("New Cron " + new Date());
    }
}
