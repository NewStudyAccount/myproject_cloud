package com.project.cloud.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 系统管理服务启动类
 */
@SpringBootApplication(scanBasePackages = {"com.project.cloud"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.project.cloud.api")
@MapperScan("com.project.cloud.system.mapper")
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
