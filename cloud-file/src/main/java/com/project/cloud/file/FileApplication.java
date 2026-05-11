package com.project.cloud.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 文件存储服务启动类
 */
@SpringBootApplication(scanBasePackages = {"com.project.cloud"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.project.cloud.api")
@MapperScan("com.project.cloud.file.mapper")
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
