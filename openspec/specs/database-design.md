# 数据库设计

## 系统模块表 (sys_*)

### 用户表 (sys_user)

```sql
CREATE TABLE sys_user (
    id              BIGINT PRIMARY KEY COMMENT '用户ID（雪花算法）',
    username        VARCHAR(64) NOT NULL COMMENT '用户名',
    password        VARCHAR(128) NOT NULL COMMENT '密码',
    nickname        VARCHAR(64) COMMENT '昵称',
    email           VARCHAR(128) COMMENT '邮箱',
    phone           VARCHAR(20) COMMENT '手机号',
    avatar          VARCHAR(255) COMMENT '头像',
    status          TINYINT DEFAULT 1 COMMENT '状态（0停用 1正常）',
    create_by       VARCHAR(64) COMMENT '创建者',
    create_time     DATETIME COMMENT '创建时间',
    update_by       VARCHAR(64) COMMENT '更新者',
    update_time     DATETIME COMMENT '更新时间',
    deleted         TINYINT DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
    version         INT DEFAULT 0 COMMENT '乐观锁'
) COMMENT '用户表';
```

### 角色表 (sys_role)

```sql
CREATE TABLE sys_role (
    id              BIGINT PRIMARY KEY COMMENT '角色ID',
    role_name       VARCHAR(64) NOT NULL COMMENT '角色名称',
    role_key        VARCHAR(64) NOT NULL COMMENT '角色权限字符串',
    sort            INT DEFAULT 0 COMMENT '排序',
    status          TINYINT DEFAULT 1 COMMENT '状态',
    create_by       VARCHAR(64),
    create_time     DATETIME,
    update_by       VARCHAR(64),
    update_time     DATETIME,
    deleted         TINYINT DEFAULT 0,
    version         INT DEFAULT 0
) COMMENT '角色表';
```

### 菜单权限表 (sys_menu)

```sql
CREATE TABLE sys_menu (
    id              BIGINT PRIMARY KEY COMMENT '菜单ID',
    menu_name       VARCHAR(64) NOT NULL COMMENT '菜单名称',
    parent_id       BIGINT DEFAULT 0 COMMENT '父菜单ID',
    sort            INT DEFAULT 0 COMMENT '排序',
    path            VARCHAR(255) COMMENT '路由路径',
    component       VARCHAR(255) COMMENT '组件路径',
    menu_type       CHAR(1) COMMENT '类型（M目录 C菜单 F按钮）',
    perms           VARCHAR(128) COMMENT '权限标识',
    icon            VARCHAR(128) COMMENT '菜单图标',
    status          TINYINT DEFAULT 1,
    create_by       VARCHAR(64),
    create_time     DATETIME,
    update_by       VARCHAR(64),
    update_time     DATETIME,
    deleted         TINYINT DEFAULT 0
) COMMENT '菜单权限表';
```

### 用户角色关联表 (sys_user_role)

```sql
CREATE TABLE sys_user_role (
    user_id         BIGINT NOT NULL COMMENT '用户ID',
    role_id         BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id)
) COMMENT '用户角色关联表';
```

### 角色菜单关联表 (sys_role_menu)

```sql
CREATE TABLE sys_role_menu (
    role_id         BIGINT NOT NULL COMMENT '角色ID',
    menu_id         BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) COMMENT '角色菜单关联表';
```

### 字典类型表 (sys_dict_type)

```sql
CREATE TABLE sys_dict_type (
    id              BIGINT PRIMARY KEY,
    dict_name       VARCHAR(128) COMMENT '字典名称',
    dict_type       VARCHAR(128) UNIQUE COMMENT '字典类型',
    status          TINYINT DEFAULT 1,
    create_by       VARCHAR(64),
    create_time     DATETIME,
    update_by       VARCHAR(64),
    update_time     DATETIME,
    deleted         TINYINT DEFAULT 0
) COMMENT '字典类型表';
```

### 字典数据表 (sys_dict_data)

```sql
CREATE TABLE sys_dict_data (
    id              BIGINT PRIMARY KEY,
    dict_type       VARCHAR(128) COMMENT '字典类型',
    dict_label      VARCHAR(128) COMMENT '字典标签',
    dict_value      VARCHAR(128) COMMENT '字典值',
    sort            INT DEFAULT 0,
    status          TINYINT DEFAULT 1,
    create_by       VARCHAR(64),
    create_time     DATETIME,
    update_by       VARCHAR(64),
    update_time     DATETIME,
    deleted         TINYINT DEFAULT 0
) COMMENT '字典数据表';
```

## OAuth2 相关表

### OAuth2 客户端表 (oauth2_registered_client)

```sql
CREATE TABLE oauth2_registered_client (
    id                          VARCHAR(128) PRIMARY KEY,
    client_id                   VARCHAR(128) NOT NULL,
    client_id_issued_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    client_secret               VARCHAR(256),
    client_name                 VARCHAR(256),
    authorization_grant_types   VARCHAR(1000),
    redirect_uris               VARCHAR(1000),
    scopes                      VARCHAR(1000),
    client_settings             VARCHAR(2000),
    token_settings              VARCHAR(2000)
) COMMENT 'OAuth2客户端表';
```

### OAuth2 授权表 (oauth2_authorization)

```sql
CREATE TABLE oauth2_authorization (
    id                          VARCHAR(128) PRIMARY KEY,
    registered_client_id        VARCHAR(128) NOT NULL,
    principal_name              VARCHAR(256),
    authorization_grant_type    VARCHAR(100),
    authorized_scopes           VARCHAR(1000),
    attributes                  VARCHAR(4000),
    state                       VARCHAR(500),
    authorization_code_value    VARCHAR(4000),
    access_token_value          VARCHAR(4000),
    access_token_issued_at      TIMESTAMP,
    access_token_expires_at     TIMESTAMP,
    refresh_token_value         VARCHAR(4000),
    refresh_token_issued_at     TIMESTAMP,
    refresh_token_expires_at    TIMESTAMP
) COMMENT 'OAuth2授权表';
```

### OAuth2 授权同意表 (oauth2_authorization_consent)

```sql
CREATE TABLE oauth2_authorization_consent (
    registered_client_id        VARCHAR(128) NOT NULL,
    principal_name              VARCHAR(256) NOT NULL,
    authorities                 VARCHAR(1000),
    PRIMARY KEY (registered_client_id, principal_name)
) COMMENT 'OAuth2授权同意表';
```

## 代码生成模块表 (gen_*)

### 数据源配置表 (gen_datasource)

```sql
CREATE TABLE gen_datasource (
    id              BIGINT PRIMARY KEY,
    name            VARCHAR(128) COMMENT '数据源名称',
    type            VARCHAR(32) COMMENT '数据源类型',
    url             VARCHAR(512) COMMENT '连接地址',
    username        VARCHAR(64) COMMENT '用户名',
    password        VARCHAR(128) COMMENT '密码',
    create_by       VARCHAR(64),
    create_time     DATETIME,
    update_by       VARCHAR(64),
    update_time     DATETIME,
    deleted         TINYINT DEFAULT 0
) COMMENT '数据源配置表';
```

### 代码生成配置表 (gen_config)

```sql
CREATE TABLE gen_config (
    id              BIGINT PRIMARY KEY,
    table_name      VARCHAR(128) COMMENT '表名',
    module_name     VARCHAR(64) COMMENT '模块名',
    package_name    VARCHAR(128) COMMENT '包名',
    entity_name     VARCHAR(64) COMMENT '实体类名',
    author          VARCHAR(64) COMMENT '作者',
    tpl_type        TINYINT DEFAULT 0 COMMENT '模板类型（0默认 1自定义）',
    create_by       VARCHAR(64),
    create_time     DATETIME,
    update_by       VARCHAR(64),
    update_time     DATETIME,
    deleted         TINYINT DEFAULT 0
) COMMENT '代码生成配置表';
```

## 文件存储模块表 (file_*)

### 文件存储配置表 (file_storage_config)

```sql
CREATE TABLE file_storage_config (
    id              BIGINT PRIMARY KEY,
    storage_type    VARCHAR(32) COMMENT '存储类型（minio/oss/cos）',
    config_name     VARCHAR(128) COMMENT '配置名称',
    endpoint        VARCHAR(255) COMMENT '端点地址',
    access_key      VARCHAR(255) COMMENT '访问密钥',
    secret_key      VARCHAR(255) COMMENT '密钥',
    bucket_name     VARCHAR(128) COMMENT '存储桶名称',
    domain          VARCHAR(255) COMMENT '访问域名',
    is_default      TINYINT DEFAULT 0 COMMENT '是否默认',
    status          TINYINT DEFAULT 1,
    create_by       VARCHAR(64),
    create_time     DATETIME,
    update_by       VARCHAR(64),
    update_time     DATETIME,
    deleted         TINYINT DEFAULT 0
) COMMENT '文件存储配置表';
```

### 文件记录表 (file_record)

```sql
CREATE TABLE file_record (
    id              BIGINT PRIMARY KEY,
    file_name       VARCHAR(255) COMMENT '文件名',
    original_name   VARCHAR(255) COMMENT '原始文件名',
    file_path       VARCHAR(512) COMMENT '文件路径',
    file_url        VARCHAR(512) COMMENT '访问URL',
    file_size       BIGINT COMMENT '文件大小（字节）',
    file_type       VARCHAR(64) COMMENT '文件类型',
    storage_type    VARCHAR(32) COMMENT '存储类型',
    storage_config_id BIGINT COMMENT '存储配置ID',
    create_by       VARCHAR(64),
    create_time     DATETIME,
    update_by       VARCHAR(64),
    update_time     DATETIME,
    deleted         TINYINT DEFAULT 0
) COMMENT '文件记录表';
```
