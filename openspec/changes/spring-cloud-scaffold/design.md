# 技术设计文档

## 1. 项目结构设计

### 1.1 模块依赖关系

```
project-cloud (父 POM)
│
├── cloud-common/
│   ├── cloud-common-core          # 核心工具，无依赖
│   ├── cloud-common-redis         # 依赖 core
│   ├── cloud-common-mybatis       # 依赖 core
│   ├── cloud-common-security      # 依赖 core, redis
│   ├── cloud-common-log           # 依赖 core
│   └── cloud-common-swagger       # 依赖 core
│
├── cloud-api/
│   ├── cloud-api-system           # 依赖 common-core
│   ├── cloud-api-file             # 依赖 common-core
│   └── cloud-api-generator        # 依赖 common-core
│
├── cloud-gateway                  # 依赖 common-core
├── cloud-auth                     # 依赖 common-security, common-redis, api-system
├── cloud-system                   # 依赖 common-mybatis, common-security, common-log, common-swagger
├── cloud-generator                # 依赖 common-mybatis, common-swagger
└── cloud-file                     # 依赖 common-mybatis, common-security, common-swagger
```

### 1.2 包结构设计

```
com.project.cloud
├── gateway/
│   ├── config/                    # 网关配置
│   ├── filter/                    # 全局过滤器
│   └── handler/                   # 异常处理
│
├── auth/
│   ├── config/                    # OAuth2 配置
│   ├── controller/                # 认证接口
│   ├── service/                   # 认证服务
│   └── domain/                    # 认证实体
│
├── system/
│   ├── controller/                # 系统管理接口
│   ├── service/                   # 业务服务
│   │   └── impl/                  # 服务实现
│   ├── mapper/                    # MyBatis Mapper
│   ├── domain/
│   │   ├── entity/                # 数据库实体
│   │   ├── vo/                    # 视图对象
│   │   ├── dto/                   # 传输对象
│   │   └── query/                 # 查询对象
│   └── convert/                   # MapStruct 转换器
│
├── generator/
│   ├── controller/
│   ├── service/
│   ├── mapper/
│   ├── domain/
│   └── template/                  # 代码模板
│
├── file/
│   ├── controller/
│   ├── service/
│   │   └── impl/
│   │       ├── MinioStorageService
│   │       ├── OssStorageService
│   │       └── CosStorageService
│   ├── mapper/
│   ├── domain/
│   └── config/
│
└── common/
    ├── core/
    │   ├── constant/              # 常量
    │   ├── enums/                 # 枚举
    │   ├── exception/             # 异常处理
    │   ├── result/                # 统一响应
    │   └── utils/                 # 工具类
    │
    ├── redis/
    │   ├── config/                # Redis 配置
    │   ├── service/               # Redis 服务
    │   └── lock/                  # 分布式锁
    │
    ├── mybatis/
    │   ├── config/                # MyBatis 配置
    │   ├── handler/               # 自动填充、乐观锁
    │   └── base/                  # 基础实体、Service
    │
    ├── security/
    │   ├── config/                # 安全配置
    │   ├── filter/                # 认证过滤器
    │   ├── service/               # 用户详情服务
    │   └── utils/                 # Token 工具
    │
    ├── log/
    │   ├── annotation/            # 日志注解
    │   ├── aspect/                # 日志切面
    │   └── event/                 # 日志事件
    │
    └── swagger/
        └── config/                # Swagger 配置
```

## 2. 认证授权设计

### 2.1 OAuth2 授权模式

```
┌─────────────────────────────────────────────────────────────────┐
│                    OAuth2 授权模式                               │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  1. 密码模式 (Password Grant)                                   │
│     ┌────────┐         ┌────────┐         ┌────────┐           │
│     │ Client │────────▶│ Auth   │────────▶│ User   │           │
│     │        │◀────────│ Server │◀────────│ Service│           │
│     └────────┘         └────────┘         └────────┘           │
│                                                                 │
│  2. 授权码模式 (Authorization Code)                             │
│     ┌────────┐         ┌────────┐         ┌────────┐           │
│     │ Client │────────▶│ Auth   │────────▶│ User   │           │
│     │        │◀────────│ Server │◀────────│ Login  │           │
│     └────────┘         └────────┘         └────────┘           │
│                                                                 │
│  3. 客户端凭证模式 (Client Credentials)                         │
│     ┌────────┐         ┌────────┐                               │
│     │ Client │────────▶│ Auth   │                               │
│     │        │◀────────│ Server │                               │
│     └────────┘         └────────┘                               │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### 2.2 Token 设计

```java
// Token 结构
{
  "sub": "user_id",           // 用户ID
  "aud": "client_id",         // 客户端ID
  "iss": "auth-server",       // 签发者
  "iat": 1234567890,          // 签发时间
  "exp": 1234567890,          // 过期时间
  "scope": ["read", "write"], // 权限范围
  "authorities": ["ADMIN"],   // 角色
  "user_id": 1,               // 用户ID
  "username": "admin"         // 用户名
}
```

### 2.3 权限验证流程

```
┌─────────────────────────────────────────────────────────────────┐
│                    权限验证流程                                   │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  1. 请求到达 Gateway                                            │
│     │                                                           │
│     ▼                                                           │
│  2. Gateway Filter 验证 Token                                   │
│     │                                                           │
│     ▼                                                           │
│  3. 转发请求到目标服务                                           │
│     │                                                           │
│     ▼                                                           │
│  4. Security Filter 验证权限                                     │
│     │                                                           │
│     ▼                                                           │
│  5. 访问资源                                                     │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## 3. 数据库设计

### 3.1 公共字段

```java
@Data
public class BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
    
    @Version
    private Integer version;
}
```

### 3.2 核心表结构

#### 用户表 (sys_user)
```sql
CREATE TABLE sys_user (
    id              BIGINT PRIMARY KEY,
    username        VARCHAR(64) NOT NULL,
    password        VARCHAR(128) NOT NULL,
    nickname        VARCHAR(64),
    email           VARCHAR(128),
    phone           VARCHAR(20),
    avatar          VARCHAR(255),
    status          TINYINT DEFAULT 1,
    create_by       VARCHAR(64),
    create_time     DATETIME,
    update_by       VARCHAR(64),
    update_time     DATETIME,
    deleted         TINYINT DEFAULT 0,
    version         INT DEFAULT 0
);
```

#### 角色表 (sys_role)
```sql
CREATE TABLE sys_role (
    id              BIGINT PRIMARY KEY,
    role_name       VARCHAR(64) NOT NULL,
    role_key        VARCHAR(64) NOT NULL,
    sort            INT DEFAULT 0,
    status          TINYINT DEFAULT 1,
    create_by       VARCHAR(64),
    create_time     DATETIME,
    update_by       VARCHAR(64),
    update_time     DATETIME,
    deleted         TINYINT DEFAULT 0,
    version         INT DEFAULT 0
);
```

#### 菜单表 (sys_menu)
```sql
CREATE TABLE sys_menu (
    id              BIGINT PRIMARY KEY,
    menu_name       VARCHAR(64) NOT NULL,
    parent_id       BIGINT DEFAULT 0,
    sort            INT DEFAULT 0,
    path            VARCHAR(255),
    component       VARCHAR(255),
    menu_type       CHAR(1),
    perms           VARCHAR(128),
    icon            VARCHAR(128),
    status          TINYINT DEFAULT 1,
    create_by       VARCHAR(64),
    create_time     DATETIME,
    update_by       VARCHAR(64),
    update_time     DATETIME,
    deleted         TINYINT DEFAULT 0
);
```

## 4. 接口设计规范

### 4.1 命名规范

所有接口使用 POST 方法，接口名体现操作含义：

```java
@RestController
@RequestMapping("/user")
public class SysUserController {
    
    @PostMapping("/detail")
    public Result<SysUserVO> detail(@RequestBody SysUserQuery query);
    
    @PostMapping("/list")
    public Result<PageResult<SysUserVO>> list(@RequestBody SysUserQuery query);
    
    @PostMapping("/add")
    public Result<Void> add(@RequestBody SysUserDTO dto);
    
    @PostMapping("/update")
    public Result<Void> update(@RequestBody SysUserDTO dto);
    
    @PostMapping("/delete")
    public Result<Void> delete(@RequestBody List<Long> ids);
    
    @PostMapping("/export")
    public void export(@RequestBody SysUserQuery query, HttpServletResponse response);
    
    @PostMapping("/import")
    public Result<Void> importData(@RequestParam MultipartFile file);
}
```

### 4.2 查询规范

使用 LambdaQueryWrapper / LambdaUpdateWrapper：

```java
// 查询
LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
wrapper.eq(SysUser::getId, userId)
       .eq(SysUser::getDeleted, 0);
SysUser user = userService.getOne(wrapper);

// 更新
LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
wrapper.eq(SysUser::getId, userId)
       .set(SysUser::getStatus, 0)
       .set(SysUser::getUpdateTime, LocalDateTime.now());
userService.update(wrapper);
```

## 5. 代码生成设计

### 5.1 生成范围

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

### 5.2 模板引擎

使用 Velocity 模板引擎生成代码：

```velocity
## Controller 模板
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
}
```

## 6. 文件存储设计

### 6.1 存储接口

```java
public interface IFileStorageService {
    
    /**
     * 上传文件
     */
    FileUploadResult upload(MultipartFile file);
    
    /**
     * 删除文件
     */
    void delete(String filePath);
    
    /**
     * 获取文件URL
     */
    String getFileUrl(String filePath);
}
```

### 6.2 多后端实现

```
┌─────────────────────────────────────────────────────────────────┐
│                    文件存储架构                                  │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────┐                                               │
│  │ FileService  │                                               │
│  └──────┬───────┘                                               │
│         │                                                       │
│         ▼                                                       │
│  ┌──────────────┐                                               │
│  │ StorageFactory│                                              │
│  └──────┬───────┘                                               │
│         │                                                       │
│    ┌────┴────┬────────────┐                                     │
│    ▼         ▼            ▼                                     │
│  ┌────┐   ┌────┐      ┌────┐                                   │
│  │MinIO│   │OSS │      │COS │                                   │
│  └────┘   └────┘      └────┘                                   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## 7. Redis 使用设计

### 7.1 缓存策略

| 场景 | Key 格式 | TTL | 说明 |
|------|---------|-----|------|
| Token 存储 | `auth:token:{userId}` | 2h | OAuth2 Token |
| 权限缓存 | `auth:perms:{userId}` | 30m | 用户权限集合 |
| 字典缓存 | `dict:{dictType}` | 24h | 字典数据 |
| 验证码 | `captcha:{uuid}` | 5m | 图形验证码 |
| 接口限流 | `rate:{apiKey}` | 1m | 接口访问计数 |
| 分布式锁 | `lock:{bizKey}` | 30s | 业务锁 |

### 7.2 分布式锁

```java
@Service
public class SysUserServiceImpl implements ISysUserService {
    
    @Autowired
    private RedissonClient redissonClient;
    
    public void updateUser(SysUserDTO dto) {
        String lockKey = "lock:user:update:" + dto.getId();
        RLock lock = redissonClient.getLock(lockKey);
        
        try {
            // 尝试加锁，最多等待3秒，锁定时间30秒
            if (lock.tryLock(3, 30, TimeUnit.SECONDS)) {
                // 执行更新操作
                doUpdateUser(dto);
            } else {
                throw new RuntimeException("获取锁失败");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("获取锁被中断");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
```

## 8. 日志设计

### 8.1 操作日志注解

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperLog {
    /** 模块名 */
    String title() default "";
    
    /** 操作类型 */
    BusinessType businessType() default BusinessType.OTHER;
    
    /** 是否保存请求参数 */
    boolean isSaveRequestData() default true;
    
    /** 是否保存响应数据 */
    boolean isSaveResponseData() default true;
}
```

### 8.2 日志切面

```java
@Aspect
@Component
public class OperLogAspect {
    
    @Around("@annotation(operLog)")
    public Object around(ProceedingJoinPoint point, OperLog operLog) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        // 执行方法
        Object result = point.proceed();
        
        // 记录操作日志
        SysOperLog log = new SysOperLog();
        log.setTitle(operLog.title());
        log.setBusinessType(operLog.businessType().ordinal());
        log.setMethod(point.getTarget().getClass().getName() + "." + point.getSignature().getName());
        log.setOperTime(new Date());
        log.setJsonResult(JSON.toJSONString(result));
        log.setStatus(0);
        
        // 异步保存日志
        AsyncManager.execute(() -> operLogService.save(log));
        
        return result;
    }
}
```

## 9. 统一响应设计

### 9.1 响应结构

```java
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;
    
    public static <T> Result<T> success() {
        return success(null);
    }
    
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }
}
```

### 9.2 分页响应

```java
@Data
public class PageResult<T> {
    private long total;
    private List<T> rows;
    
    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
```

## 10. 异常处理设计

### 10.1 全局异常处理

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        return Result.error(e.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(message);
    }
    
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error("系统异常，请联系管理员");
    }
}
```

## 11. Docker Compose 设计

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0.33
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: root123
      MYSQL_DATABASE: project_cloud
    volumes:
      - ./mysql/data:/var/lib/mysql
      - ./mysql/init:/docker-entrypoint-initdb.d

  redis:
    image: redis:7.2
    ports:
      - "6379:6379"
    command: redis-server --requirepass redis123

  nacos:
    image: nacos/nacos-server:v2.4.3
    ports:
      - "8848:8848"
      - "9848:9848"
    environment:
      MODE: standalone
      SPRING_DATASOURCE_PLATFORM: mysql
      MYSQL_SERVICE_HOST: mysql
      MYSQL_SERVICE_DB_NAME: nacos
      MYSQL_SERVICE_USER: root
      MYSQL_SERVICE_PASSWORD: root123

  minio:
    image: minio/minio:latest
    ports:
      - "9000:9000"
      - "9001:9001"
    environment:
      MINIO_ROOT_USER: minioadmin
      MINIO_ROOT_PASSWORD: minioadmin
    command: server /data --console-address ":9001"
```
