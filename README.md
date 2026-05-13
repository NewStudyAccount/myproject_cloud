# Project Cloud - Spring Cloud 微服务脚手架

## 项目简介

基于 Spring Cloud 技术栈搭建的微服务脚手架工程，包含后端基础框架和前端基础框架。

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

## 项目结构

```
project-cloud/
├── cloud-gateway/           # API 网关服务 (8080)
├── cloud-auth/              # 认证服务 (9200)
├── cloud-system/            # 系统管理服务 (9201)
├── cloud-generator/         # 代码生成服务 (9202)
├── cloud-file/              # 文件存储服务 (9203)
├── cloud-common/            # 公共模块
│   ├── cloud-common-core/   # 核心工具
│   ├── cloud-common-redis/  # Redis 封装
│   ├── cloud-common-mybatis/# MyBatis 封装
│   ├── cloud-common-security/# 安全模块
│   ├── cloud-common-log/    # 日志模块
│   └── cloud-common-swagger/# API 文档
├── cloud-api/               # Feign 接口
│   ├── cloud-api-system/
│   ├── cloud-api-file/
│   └── cloud-api-generator/
├── cloud-ui/                # 前端项目
│   ├── src/
│   │   ├── api/             # API 接口
│   │   ├── assets/          # 静态资源
│   │   ├── components/      # 公共组件
│   │   ├── layouts/         # 布局组件
│   │   ├── router/          # 路由配置
│   │   ├── store/           # 状态管理
│   │   ├── utils/           # 工具类
│   │   └── views/           # 页面组件
│   └── package.json
└── docker/                  # Docker 配置
```

## 快速开始

### 1. 启动基础服务

```bash
cd docker
./start.sh
```

启动后访问：
- Nacos: http://127.0.0.1:8848/nacos (nacos/nacos)
- MinIO: http://127.0.0.1:9001 (minioadmin/minioadmin)

### 2. 启动后端服务

按以下顺序启动服务：

1. cloud-gateway (8080)
2. cloud-auth (9200)
3. cloud-system (9201)
4. cloud-generator (9202)
5. cloud-file (9203)

### 3. 启动前端

```bash
cd cloud-ui
npm install
npm run dev
```

### 4. 访问服务

- 前端地址: http://localhost:3000
- API 网关: http://localhost:8080
- API 文档: http://localhost:8080/doc.html

## 接口规范

所有接口使用 POST 方法，接口名体现操作含义：

```
POST /user/detail        # 查询详情
POST /user/list          # 查询列表
POST /user/add           # 新增
POST /user/update        # 更新
POST /user/delete        # 删除
POST /user/export        # 导出
POST /user/import        # 导入
```

查询使用 LambdaQueryWrapper / LambdaUpdateWrapper，不使用 ById 方式。

## 默认账号

- 管理员: admin / 123456

## 开发规范

### 代码生成

支持生成以下代码：
- Entity - 数据库实体
- VO - 视图对象
- DTO - 传输对象
- Query - 查询对象
- Mapper - MyBatis Mapper
- Service - 服务接口
- ServiceImpl - 服务实现
- Controller - 控制器
- Vue Page - Vue3 页面
- Vue API - TypeScript API

### 日志记录

使用 `@OperLog` 注解记录操作日志：

```java
@OperLog(title = "用户管理", businessType = BusinessType.INSERT)
public Result<Void> add(@RequestBody SysUserDTO dto) {
    // ...
}
```

## 文档

- [架构总览](openspec/specs/architecture-overview.md)
- [数据库设计](openspec/specs/database-design.md)
- [模块设计](openspec/specs/module-design.md)
- [变更提案](openspec/changes/spring-cloud-scaffold/proposal.md)
- [技术设计](openspec/changes/spring-cloud-scaffold/design.md)
- [任务清单](openspec/changes/spring-cloud-scaffold/tasks.md)
