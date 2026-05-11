# 变更提案: Spring Cloud 脚手架工程

## 变更概述

搭建基于 Spring Cloud 技术的微服务脚手架工程，包含后端基础框架和前端基础框架。

## 变更目标

1. 搭建完整的微服务架构骨架
2. 实现基础的用户认证授权功能
3. 实现系统管理基础功能（用户、角色、菜单、字典）
4. 实现代码生成功能
5. 实现文件存储功能（多后端支持）
6. 搭建前端基础框架

## 技术栈

### 后端
- Java 21 + Maven
- Spring Boot 3.5.x + Spring Cloud 2025.0.0 + Spring Cloud Alibaba 2025.0.0.0
- Spring Authorization Server 1.4.0 (OAuth2)
- MyBatis-Plus 3.5.9 + Druid 1.2.23
- Redisson 3.30.0 (Redis)
- Nacos 2.4.x (注册/配置中心)
- Sentinel 1.8.8 (熔断降级)

### 前端
- Vue 3.5.x + TypeScript 5.6.x
- Element Plus 2.8.x
- Vite 6.0.x
- Pinia 2.2.x + Vue Router 4.4.x

## 模块划分

### 后端模块
1. **cloud-gateway** - API 网关服务 (8080)
2. **cloud-auth** - 认证服务 (9200)
3. **cloud-system** - 系统管理服务 (9201)
4. **cloud-generator** - 代码生成服务 (9202)
5. **cloud-file** - 文件存储服务 (9203)
6. **cloud-common** - 公共模块
   - cloud-common-core
   - cloud-common-redis
   - cloud-common-mybatis
   - cloud-common-security
   - cloud-common-log
   - cloud-common-swagger
7. **cloud-api** - Feign 接口
   - cloud-api-system
   - cloud-api-file
   - cloud-api-generator

### 前端模块
- Vue3 + TypeScript + Element Plus 基础框架

## 实现阶段

### 阶段一：基础框架搭建
1. 创建父 POM 和模块结构
2. 实现 cloud-common 公共模块
3. 实现 cloud-gateway 网关服务
4. 实现 cloud-auth 认证服务
5. 配置 Nacos 注册/配置中心

### 阶段二：系统管理功能
1. 实现用户管理
2. 实现角色管理
3. 实现菜单管理
4. 实现字典管理

### 阶段三：代码生成功能
1. 实现数据源管理
2. 实现代码生成配置
3. 实现代码生成器
4. 实现前端页面生成

### 阶段四：文件存储功能
1. 实现文件存储配置
2. 实现 MinIO 存储服务
3. 实现阿里云 OSS 存储服务
4. 实现腾讯云 COS 存储服务

### 阶段五：前端框架
1. 搭建 Vue3 基础框架
2. 实现登录页面
3. 实现系统管理页面
4. 实现代码生成页面

## 预期成果

1. 完整的微服务脚手架工程
2. 可运行的后端服务
3. 可运行的前端应用
4. 完整的数据库脚本
5. Docker Compose 本地开发环境

## 风险评估

1. **技术复杂度**：微服务架构涉及多个组件，需要确保版本兼容性
2. **学习曲线**：Spring Authorization Server 相对较新，可能需要额外学习
3. **集成难度**：多个组件集成可能遇到配置问题

## 成功标准

1. 所有服务能够正常启动并注册到 Nacos
2. 用户能够通过 OAuth2 认证获取 Token
3. 系统管理功能正常运行
4. 代码生成功能能够生成可运行的代码
5. 文件存储功能支持多后端
6. 前端应用能够正常访问后端服务
