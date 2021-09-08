package com.moqi.scheduleminiprogrambackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.moqi.scheduleminiprogrambackend.mapperService")
public class ScheduleMiniProgramBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleMiniProgramBackendApplication.class, args);
    }

}
