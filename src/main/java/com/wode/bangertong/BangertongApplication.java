package com.wode.bangertong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.wode.bangertong.mapper"})
public class BangertongApplication {
    public static void main(String[] args) {
        SpringApplication.run(BangertongApplication.class, args);
    }

}
