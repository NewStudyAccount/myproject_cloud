package com.project.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 认证服务启动类
 */
@SpringBootApplication(scanBasePackages = {"com.project.cloud"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.project.cloud.api")
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
