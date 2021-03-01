package com.km.redisboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.km.redisboot.mapper")
public class RedisBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisBootApplication.class, args);
    }

}
