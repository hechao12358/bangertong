package com.wode.bangertongadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.wode.bangertongadmin.mapper"})
public class BangertongAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BangertongAdminApplication.class, args);
    }

}
