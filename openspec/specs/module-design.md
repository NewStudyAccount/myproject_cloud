# 模块设计

## 项目结构

```
project-cloud/
│
├── pom.xml                              # 父 POM（版本管理）
│
├── cloud-gateway/                       # API 网关服务
│   └── src/main/java/
│       └── com.project.cloud.gateway
│           ├── config/                  # 网关配置
│           ├── filter/                  # 全局过滤器
│           └── handler/                 # 异常处理
│
├── cloud-auth/                          # 认证服务
│   └── src/main/java/
│       └── com.project.cloud.auth
│           ├── config/                  # OAuth2 配置
│           ├── controller/              # 认证接口
│           ├── service/                 # 认证服务
│           └── domain/                  # 认证实体
│
├── cloud-system/                        # 系统管理服务
│   └── src/main/java/
│       └── com.project.cloud.system
│           ├── controller/
│           │   ├── SysUserController    # 用户管理
│           │   ├── SysRoleController    # 角色管理
│           │   ├── SysMenuController    # 菜单管理
│           │   └── SysDictController    # 字典管理
│           ├── service/
│           │   ├── impl/
│           │   └── ISysUserService
│           ├── mapper/
│           ├── domain/
│           │   ├── entity/              # 数据库实体
│           │   ├── vo/                  # 视图对象
│           │   ├── dto/                 # 传输对象
│           │   └── query/               # 查询对象
│           └── convert/                 # MapStruct 转换器
│
├── cloud-generator/                     # 代码生成服务
│   └── src/main/java/
│       └── com.project.cloud.generator
│           ├── controller/
│           ├── service/
│           │   ├── IGenConfigService
│           │   ├── IGenDatasourceService
│           │   └── ICodeGeneratorService
│           ├── mapper/
│           ├── domain/
│           └── template/                # 代码模板（Velocity）
│               ├── java/                # Java 模板
│               └── vue/                 # Vue 模板
│
├── cloud-file/                          # 文件存储服务
│   └── src/main/java/
│       └── com.project.cloud.file
│           ├── controller/
│           ├── service/
│           │   ├── IFileStorageService
│           │   ├── IFileRecordService
│           │   └── impl/
│           │       ├── MinioStorageService
│           │       ├── OssStorageService
│           │       └── CosStorageService
│           ├── mapper/
│           ├── domain/
│           └── config/
│
├── cloud-common/                        # 公共模块
│   ├── cloud-common-core/               # 核心工具
│   │   └── src/main/java/
│   │       └── com.project.cloud.common.core
│   │           ├── constant/            # 常量
│   │           ├── enums/               # 枚举
│   │           ├── exception/           # 异常处理
│   │           ├── result/              # 统一响应
│   │           └── utils/               # 工具类
│   │
│   ├── cloud-common-redis/              # Redis 封装
│   │   └── src/main/java/
│   │       └── com.project.cloud.common.redis
│   │           ├── config/              # Redis 配置
│   │           ├── service/             # Redis 服务
│   │           └── lock/                # 分布式锁
│   │
│   ├── cloud-common-mybatis/            # MyBatis-Plus 封装
│   │   └── src/main/java/
│   │       └── com.project.cloud.common.mybatis
│   │           ├── config/              # MyBatis 配置
│   │           ├── handler/             # 自动填充、乐观锁
│   │           └── base/                # 基础实体、Service
│   │
│   ├── cloud-common-security/           # 安全模块
│   │   └── src/main/java/
│   │       └── com.project.cloud.common.security
│   │           ├── config/              # 安全配置
│   │           ├── filter/              # 认证过滤器
│   │           ├── service/             # 用户详情服务
│   │           └── utils/               # Token 工具
│   │
│   ├── cloud-common-log/                # 日志模块
│   │   └── src/main/java/
│   │       └── com.project.cloud.common.log
│   │           ├── annotation/          # 日志注解
│   │           ├── aspect/              # 日志切面
│   │           └── event/               # 日志事件
│   │
│   └── cloud-common-swagger/            # API 文档
│       └── src/main/java/
│           └── com.project.cloud.common.swagger
│               └── config/              # Swagger 配置
│
├── cloud-api/                           # Feign 接口
│   ├── cloud-api-system/                # 系统服务 API
│   │   └── src/main/java/
│   │       └── com.project.cloud.api.system
│   │           ├── RemoteUserService
│   │           ├── RemoteRoleService
│   │           └── fallback/            # 降级处理
│   │
│   ├── cloud-api-file/                  # 文件服务 API
│   │   └── src/main/java/
│   │       └── com.project.cloud.api.file
│   │           ├── RemoteFileService
│   │           └── fallback/
│   │
│   └── cloud-api-generator/             # 代码生成 API
│       └── src/main/java/
│           └── com.project.cloud.api.generator
│               ├── RemoteGeneratorService
│               └── fallback/
│
└── docker/                              # Docker 配置
    ├── docker-compose.yml
    ├── mysql/
    ├── redis/
    ├── nacos/
    └── minio/
```

## 模块依赖关系

```
┌─────────────────────────────────────────────────────────────────┐
│                      模块依赖关系                                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  cloud-gateway ─────────────────────────────────────────────┐   │
│       │                                                     │   │
│       └──▶ cloud-common-core                                │   │
│                                                                 │
│  cloud-auth ────────────────────────────────────────────────┐   │
│       │                                                     │   │
│       ├──▶ cloud-common-core                                │   │
│       ├──▶ cloud-common-redis                               │   │
│       ├──▶ cloud-common-security                            │   │
│       └──▶ cloud-api-system                                 │   │
│                                                                 │
│  cloud-system ──────────────────────────────────────────────┐   │
│       │                                                     │   │
│       ├──▶ cloud-common-core                                │   │
│       ├──▶ cloud-common-redis                               │   │
│       ├──▶ cloud-common-mybatis                             │   │
│       ├──▶ cloud-common-security                            │   │
│       ├──▶ cloud-common-log                                 │   │
│       └──▶ cloud-common-swagger                             │   │
│                                                                 │
│  cloud-generator ───────────────────────────────────────────┐   │
│       │                                                     │   │
│       ├──▶ cloud-common-core                                │   │
│       ├──▶ cloud-common-mybatis                             │   │
│       └──▶ cloud-common-swagger                             │   │
│                                                                 │
│  cloud-file ────────────────────────────────────────────────┐   │
│       │                                                     │   │
│       ├──▶ cloud-common-core                                │   │
│       ├──▶ cloud-common-mybatis                             │   │
│       ├──▶ cloud-common-security                            │   │
│       └──▶ cloud-common-swagger                             │   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## 服务接口规范

### 接口命名

所有接口使用 POST 方法，接口名体现操作含义：

```java
@RestController
@RequestMapping("/user")
public class SysUserController {

    @PostMapping("/detail")
    public Result<SysUserVO> detail(@RequestBody SysUserQuery query) {
        // 查询用户详情
    }

    @PostMapping("/list")
    public Result<PageResult<SysUserVO>> list(@RequestBody SysUserQuery query) {
        // 查询用户列表（分页）
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody SysUserDTO dto) {
        // 新增用户
    }

    @PostMapping("/update")
    public Result<Void> update(@RequestBody SysUserDTO dto) {
        // 更新用户
    }

    @PostMapping("/delete")
    public Result<Void> delete(@RequestBody List<Long> ids) {
        // 批量删除用户
    }

    @PostMapping("/export")
    public void export(@RequestBody SysUserQuery query, HttpServletResponse response) {
        // 导出用户数据
    }

    @PostMapping("/import")
    public Result<Void> importData(@RequestParam MultipartFile file) {
        // 导入用户数据
    }
}
```

### 查询方式

使用 LambdaQueryWrapper / LambdaUpdateWrapper：

```java
// 查询单个
LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
wrapper.eq(SysUser::getId, userId)
       .eq(SysUser::getDeleted, 0);
SysUser user = userService.getOne(wrapper);

// 查询列表
LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
wrapper.like(StringUtils.isNotBlank(query.getUsername()), 
             SysUser::getUsername, query.getUsername())
       .eq(SysUser::getStatus, 1)
       .orderByDesc(SysUser::getCreateTime);
List<SysUser> list = userService.list(wrapper);

// 更新
LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
wrapper.eq(SysUser::getId, userId)
       .set(SysUser::getStatus, 0)
       .set(SysUser::getUpdateTime, LocalDateTime.now());
userService.update(wrapper);

// 删除（逻辑删除）
LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
wrapper.in(SysUser::getId, ids)
       .set(SysUser::getDeleted, 1);
userService.update(wrapper);
```

## 代码生成模板

### 生成范围

| 层级 | 生成内容 | 说明 |
|------|---------|------|
| Entity | 数据库实体 | 对应数据库表 |
| VO | 视图对象 | 返回给前端 |
| DTO | 传输对象 | 前端提交数据 |
| Query | 查询对象 | 查询参数封装 |
| Mapper | MyBatis Mapper | 数据访问层 |
| Service | 服务接口 | 业务逻辑接口 |
| ServiceImpl | 服务实现 | 业务逻辑实现 |
| Controller | 控制器 | 接口层 |
| Vue Page | Vue3 页面 | 列表 + 表单 |
| Vue API | TypeScript API | 接口调用 |

### 生成示例

```java
// Controller 模板
@RestController
@RequestMapping("/${moduleName}/${entityNameUncap}")
public class ${entityName}Controller {

    @Autowired
    private I${entityName}Service ${entityNameUncap}Service;

    @PostMapping("/detail")
    @Operation(summary = "查询详情")
    public Result<${entityName}VO> detail(@RequestBody ${entityName}Query query) {
        return Result.success(${entityNameUncap}Service.detail(query));
    }

    @PostMapping("/list")
    @Operation(summary = "查询列表")
    public Result<PageResult<${entityName}VO>> list(@RequestBody ${entityName}Query query) {
        return Result.success(${entityNameUncap}Service.list(query));
    }

    @PostMapping("/add")
    @Operation(summary = "新增")
    public Result<Void> add(@RequestBody @Valid ${entityName}DTO dto) {
        ${entityNameUncap}Service.add(dto);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新")
    public Result<Void> update(@RequestBody @Valid ${entityName}DTO dto) {
        ${entityNameUncap}Service.update(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除")
    public Result<Void> delete(@RequestBody @NotEmpty List<Long> ids) {
        ${entityNameUncap}Service.delete(ids);
        return Result.success();
    }
}
```
