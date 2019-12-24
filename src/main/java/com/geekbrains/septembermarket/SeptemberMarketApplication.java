package com.geekbrains.septembermarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.geekbrains.septembermarket"})
public class SeptemberMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeptemberMarketApplication.class, args);
    }
}
