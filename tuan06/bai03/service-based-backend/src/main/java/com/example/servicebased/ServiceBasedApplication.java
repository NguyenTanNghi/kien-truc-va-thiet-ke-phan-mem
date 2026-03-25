package com.example.servicebased;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ServiceBasedApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceBasedApplication.class, args);
    }
}
