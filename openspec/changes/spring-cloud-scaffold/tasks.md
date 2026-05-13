# 任务清单

## 阶段一：基础框架搭建

### 1.1 创建项目结构
- [x] 创建父 POM (pom.xml)
- [x] 创建 cloud-common 模块
- [x] 创建 cloud-api 模块
- [x] 创建 cloud-gateway 模块
- [x] 创建 cloud-auth 模块
- [x] 创建 cloud-system 模块
- [x] 创建 cloud-generator 模块
- [x] 创建 cloud-file 模块

### 1.2 实现 cloud-common-core
- [x] 创建常量类
- [x] 创建枚举类
- [x] 创建统一响应类 (Result, PageResult)
- [x] 创建全局异常处理
- [x] 创建工具类 (日期、字符串、JSON等)

### 1.3 实现 cloud-common-redis
- [x] 创建 Redis 配置类
- [x] 创建 Redis 服务类
- [x] 创建分布式锁工具类

### 1.4 实现 cloud-common-mybatis
- [x] 创建 MyBatis-Plus 配置类
- [x] 创建基础实体类 (BaseEntity)
- [x] 创建基础 Service 类
- [x] 创建自动填充处理器
- [x] 创建乐观锁插件配置

### 1.5 实现 cloud-common-security
- [x] 创建安全配置类
- [x] 创建 Token 解析过滤器
- [x] 创建用户详情服务
- [x] 创建权限验证工具类

### 1.6 实现 cloud-common-log
- [x] 创建操作日志注解
- [x] 创建日志切面
- [x] 创建异步任务管理器

### 1.7 实现 cloud-common-swagger
- [x] 创建 Swagger 配置类
- [x] 创建 API 文档配置

### 1.8 实现 cloud-gateway
- [x] 创建网关配置
- [x] 创建全局过滤器
- [x] 创建异常处理
- [x] 配置路由规则

### 1.9 实现 cloud-auth
- [x] 创建 OAuth2 配置
- [x] 创建认证控制器
- [x] 创建用户详情服务
- [x] 创建 Token 服务

### 1.10 配置 Nacos
- [x] 创建 Nacos 配置文件
- [x] 配置服务注册
- [x] 配置配置中心

## 阶段二：系统管理功能

### 2.1 用户管理
- [x] 创建用户实体类 (SysUser)
- [x] 创建用户 VO/DTO/Query
- [x] 创建用户 Mapper
- [x] 创建用户 Service 接口
- [x] 创建用户 Service 实现
- [x] 创建用户 Controller
- [x] 创建 MapStruct 转换器

### 2.2 角色管理
- [x] 创建角色实体类 (SysRole)
- [x] 创建角色 VO/DTO/Query
- [x] 创建角色 Mapper
- [x] 创建角色 Service 接口
- [x] 创建角色 Service 实现
- [x] 创建角色 Controller

### 2.3 菜单管理
- [x] 创建菜单实体类 (SysMenu)
- [x] 创建菜单 VO/DTO/Query
- [x] 创建菜单 Mapper
- [x] 创建菜单 Service 接口
- [x] 创建菜单 Service 实现
- [x] 创建菜单 Controller

### 2.4 字典管理
- [x] 创建字典类型实体类 (SysDictType)
- [x] 创建字典数据实体类 (SysDictData)
- [x] 创建字典 VO/DTO/Query
- [x] 创建字典 Mapper
- [x] 创建字典 Service 接口
- [x] 创建字典 Service 实现
- [x] 创建字典 Controller

### 2.5 实现 cloud-api-system
- [x] 创建 Feign 接口
- [x] 创建降级处理

## 阶段三：代码生成功能

### 3.1 数据源管理
- [x] 创建数据源实体类 (GenDatasource)
- [x] 创建数据源 VO/DTO/Query
- [x] 创建数据源 Mapper
- [x] 创建数据源 Service
- [x] 创建数据源 Controller

### 3.2 代码生成配置
- [x] 创建生成配置实体类 (GenConfig)
- [x] 创建生成配置 VO/DTO/Query
- [x] 创建生成配置 Mapper
- [x] 创建生成配置 Service
- [x] 创建生成配置 Controller

### 3.3 代码生成器
- [x] 创建代码生成服务
- [x] 创建 Velocity 模板
  - [x] Entity 模板
  - [x] VO 模板
  - [x] DTO 模板
  - [x] Query 模板
  - [x] Mapper 模板
  - [x] Service 模板
  - [x] ServiceImpl 模板
  - [x] Controller 模板
  - [x] Vue Page 模板
  - [x] Vue API 模板

### 3.4 实现 cloud-api-generator
- [x] 创建 Feign 接口
- [x] 创建降级处理

## 阶段四：文件存储功能

### 4.1 文件存储配置
- [x] 创建存储配置实体类 (FileStorageConfig)
- [x] 创建存储配置 VO/DTO/Query
- [x] 创建存储配置 Mapper
- [x] 创建存储配置 Service
- [x] 创建存储配置 Controller

### 4.2 文件记录
- [x] 创建文件记录实体类 (FileRecord)
- [x] 创建文件记录 VO/DTO/Query
- [x] 创建文件记录 Mapper
- [x] 创建文件记录 Service
- [x] 创建文件记录 Controller

### 4.3 存储服务实现
- [x] 创建存储接口 (IFileStorageService)
- [x] 创建存储工厂 (StorageFactory)
- [x] 创建 MinIO 存储服务
- [x] 创建阿里云 OSS 存储服务
- [x] 创建腾讯云 COS 存储服务

### 4.4 实现 cloud-api-file
- [x] 创建 Feign 接口
- [x] 创建降级处理

## 阶段五：前端框架

### 5.1 搭建基础框架
- [x] 创建 Vue3 项目
- [x] 配置 TypeScript
- [x] 配置 Vite
- [x] 配置 Element Plus
- [x] 配置 Vue Router
- [x] 配置 Pinia
- [x] 配置 Axios

### 5.2 实现登录页面
- [x] 创建登录页面
- [x] 实现登录逻辑
- [x] 实现 Token 存储
- [x] 实现路由守卫

### 5.3 实现系统管理页面
- [x] 用户管理页面
- [x] 角色管理页面
- [x] 菜单管理页面
- [x] 字典管理页面

### 5.4 实现代码生成页面
- [x] 数据源管理页面
- [x] 代码生成配置页面
- [x] 代码预览页面

### 5.5 实现文件管理页面
- [x] 文件上传页面
- [x] 文件列表页面
- [x] 文件预览页面

## 阶段六：Docker 部署

### 6.1 创建 Docker 配置
- [x] 创建 docker-compose.yml
- [x] 创建 MySQL 初始化脚本
- [x] 创建 Redis 配置
- [x] 创建 Nacos 配置
- [x] 创建 MinIO 配置

### 6.2 创建启动脚本
- [x] 创建启动脚本
- [x] 创建停止脚本
- [x] 创建重启脚本

## 阶段七：测试与文档

### 7.1 单元测试
- [x] 创建 Service 单元测试
- [x] 创建 Controller 单元测试

### 7.2 集成测试
- [x] 创建 API 集成测试
- [x] 创建数据库集成测试

### 7.3 文档
- [x] 创建 README.md
- [x] 创建 API 文档
- [x] 创建部署文档
- [x] 创建开发文档
