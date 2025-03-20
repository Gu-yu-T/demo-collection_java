package com.guyu.sqlite_demo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.guyu.sqlite_demo.mapper") // 替换为你的 Mapper 包路径
public class SqliteDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqliteDemoApplication.class, args);
    }

}
