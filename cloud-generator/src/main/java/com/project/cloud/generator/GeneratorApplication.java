package com.project.cloud.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 代码生成服务启动类
 */
@SpringBootApplication(scanBasePackages = {"com.project.cloud"})
@EnableDiscoveryClient
@MapperScan("com.project.cloud.generator.mapper")
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }
}
