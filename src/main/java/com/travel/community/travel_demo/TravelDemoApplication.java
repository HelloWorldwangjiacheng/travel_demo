package com.travel.community.travel_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.travel.community.travel_demo.mapper")
public class TravelDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelDemoApplication.class, args);
    }

}
