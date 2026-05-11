# 项目技术架构总览

## 架构图

```
┌─────────────────────────────────────────────────────────────────────────┐
│                          架构总览                                        │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   ┌──────────────┐     ┌──────────────┐     ┌──────────────┐           │
│   │   Vue3/TS    │────▶│  Nginx       │────▶│   Gateway    │           │
│   │   前端应用    │     │  反向代理     │     │   (8080)     │           │
│   └──────────────┘     └──────────────┘     └──────┬───────┘           │
│                                                     │                   │
│                      ┌──────────────────────────────┼──────────────┐    │
│                      │                              │              │    │
│                      ▼                              ▼              ▼    │
│              ┌──────────────┐             ┌──────────────┐ ┌────────────┐│
│              │  Auth Server │             │   System     │ │  Generator ││
│              │   (9200)     │             │   (9201)     │ │  (9202)    ││
│              │  OAuth2 认证  │             │  系统管理     │ │  代码生成   ││
│              └──────────────┘             └──────────────┘ └────────────┘│
│                      │                              │              │    │
│                      │                              ▼              │    │
│                      │                      ┌──────────────┐       │    │
│                      │                      │    File      │       │    │
│                      │                      │   (9203)     │       │    │
│                      │                      │  文件存储     │       │    │
│                      │                      └──────────────┘       │    │
│                      │                              │              │    │
│                      ▼                              ▼              ▼    │
│              ┌──────────────┐             ┌──────────────┐              │
│              │    Nacos     │             │    MySQL     │              │
│              │  注册/配置    │             │    8.0       │              │
│              └──────────────┘             └──────────────┘              │
│                                              │                         │
│                                              ▼                         │
│                                      ┌──────────────┐                  │
│                                      │    Redis     │                  │
│                                      │    7.x       │                  │
│                                      └──────────────┘                  │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

## 技术栈

| 组件 | 技术选型 | 版本 |
|------|---------|------|
| JDK | OpenJDK | 21 |
| 构建工具 | Maven | 3.9.9 |
| 基础框架 | Spring Boot | 3.5.x |
| 微服务 | Spring Cloud | 2025.0.0 |
| 微服务 | Spring Cloud Alibaba | 2025.0.0.0 |
| 注册/配置中心 | Nacos | 2.4.x |
| API 网关 | Spring Cloud Gateway | - |
| 服务调用 | OpenFeign | - |
| 负载均衡 | Spring Cloud LoadBalancer | - |
| 熔断降级 | Sentinel | 1.8.8 |
| 认证授权 | Spring Authorization Server | 1.4.0 |
| ORM | MyBatis-Plus | 3.5.9 |
| 连接池 | Druid | 1.2.23 |
| 数据库 | MySQL | 8.0.33 |
| 缓存 | Redis | 7.2 |
| Redis 客户端 | Redisson | 3.30.0 |
| 对象存储 | MinIO | 8.5.10 |
| API 文档 | Knife4j | 4.5.0 |
| 对象映射 | MapStruct | 1.6.3 |
| 工具库 | Hutool | 5.8.32 |
| Excel 处理 | EasyExcel | 4.0.3 |
| 前端框架 | Vue 3 + TypeScript | 3.5.x |
| UI 组件库 | Element Plus | 2.8.x |
| 构建工具 | Vite | 6.0.x |
| 状态管理 | Pinia | 2.2.x |
| 路由 | Vue Router | 4.4.x |
| HTTP 请求 | Axios | 1.7.x |

## 服务端口规划

| 服务 | 端口 | 说明 |
|------|------|------|
| cloud-gateway | 8080 | API 网关 |
| cloud-auth | 9200 | 认证服务 |
| cloud-system | 9201 | 系统管理 |
| cloud-generator | 9202 | 代码生成 |
| cloud-file | 9203 | 文件存储 |
| Nacos | 8848 | 注册/配置中心 |
| MySQL | 3306 | 数据库 |
| Redis | 6379 | 缓存 |
| RocketMQ | 9876 | 消息队列 |
| MinIO | 9000 | 文件存储 |

## 接口规范

### 命名规则

- 所有接口使用 POST 方法
- 接口名体现操作含义

```
POST /user/detail        # 查询详情
POST /user/list          # 查询列表
POST /user/add           # 新增
POST /user/update        # 更新
POST /user/delete        # 删除
POST /user/export        # 导出
POST /user/import        # 导入
```

### 查询方式

使用 LambdaQueryWrapper / LambdaUpdateWrapper，不使用 ById 方式：

```java
// 查询
LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
wrapper.eq(User::getId, userId);
User user = userService.getOne(wrapper);

// 更新
LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
wrapper.eq(User::getId, userId)
       .set(User::getStatus, 0);
userService.update(wrapper);
```

## Redis 使用场景

| 场景 | Key 格式 | 说明 |
|------|---------|------|
| Token 存储 | `auth:token:{userId}` | OAuth2 Token |
| 权限缓存 | `auth:perms:{userId}` | 用户权限集合 |
| 字典缓存 | `dict:{dictType}` | 字典数据 |
| 验证码 | `captcha:{uuid}` | 图形验证码 |
| 接口限流 | `rate:{apiKey}` | 接口访问计数 |
| 分布式锁 | `lock:{bizKey}` | 业务锁 |

## 数据库规范

### 公共字段

```sql
id              BIGINT PRIMARY KEY    -- 雪花算法ID
create_by       VARCHAR(64)           -- 创建者
create_time     DATETIME              -- 创建时间
update_by       VARCHAR(64)           -- 更新者
update_time     DATETIME              -- 更新时间
deleted         TINYINT DEFAULT 0     -- 逻辑删除（0存在 1删除）
version         INT DEFAULT 0         -- 乐观锁
```

### 表名前缀

| 模块 | 前缀 | 示例 |
|------|------|------|
| 系统模块 | sys_ | sys_user, sys_role |
| 代码生成 | gen_ | gen_config, gen_datasource |
| 文件模块 | file_ | file_record, file_storage_config |
| OAuth2 | oauth2_ | oauth2_registered_client |
