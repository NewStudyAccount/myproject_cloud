-- 创建数据库
CREATE DATABASE IF NOT EXISTS `project_cloud` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `project_cloud`;

-- 用户表
CREATE TABLE IF NOT EXISTS `sys_user` (
    `id` BIGINT PRIMARY KEY COMMENT '用户ID（雪花算法）',
    `username` VARCHAR(64) NOT NULL COMMENT '用户名',
    `password` VARCHAR(128) NOT NULL COMMENT '密码',
    `nickname` VARCHAR(64) COMMENT '昵称',
    `email` VARCHAR(128) COMMENT '邮箱',
    `phone` VARCHAR(20) COMMENT '手机号',
    `avatar` VARCHAR(255) COMMENT '头像',
    `status` TINYINT DEFAULT 1 COMMENT '状态（0停用 1正常）',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
    `version` INT DEFAULT 0 COMMENT '乐观锁',
    UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS `sys_role` (
    `id` BIGINT PRIMARY KEY COMMENT '角色ID',
    `role_name` VARCHAR(64) NOT NULL COMMENT '角色名称',
    `role_key` VARCHAR(64) NOT NULL COMMENT '角色权限字符串',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `status` TINYINT DEFAULT 1 COMMENT '状态',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志',
    `version` INT DEFAULT 0 COMMENT '乐观锁',
    UNIQUE KEY `idx_role_key` (`role_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 菜单权限表
CREATE TABLE IF NOT EXISTS `sys_menu` (
    `id` BIGINT PRIMARY KEY COMMENT '菜单ID',
    `menu_name` VARCHAR(64) NOT NULL COMMENT '菜单名称',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父菜单ID',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `path` VARCHAR(255) COMMENT '路由路径',
    `component` VARCHAR(255) COMMENT '组件路径',
    `menu_type` CHAR(1) COMMENT '类型（M目录 C菜单 F按钮）',
    `perms` VARCHAR(128) COMMENT '权限标识',
    `icon` VARCHAR(128) COMMENT '菜单图标',
    `status` TINYINT DEFAULT 1 COMMENT '状态',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单权限表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS `sys_user_role` (
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关联表';

-- 字典类型表
CREATE TABLE IF NOT EXISTS `sys_dict_type` (
    `id` BIGINT PRIMARY KEY COMMENT '字典类型ID',
    `dict_name` VARCHAR(128) COMMENT '字典名称',
    `dict_type` VARCHAR(128) UNIQUE COMMENT '字典类型',
    `status` TINYINT DEFAULT 1 COMMENT '状态',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典类型表';

-- 字典数据表
CREATE TABLE IF NOT EXISTS `sys_dict_data` (
    `id` BIGINT PRIMARY KEY COMMENT '字典数据ID',
    `dict_type` VARCHAR(128) COMMENT '字典类型',
    `dict_label` VARCHAR(128) COMMENT '字典标签',
    `dict_value` VARCHAR(128) COMMENT '字典值',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `status` TINYINT DEFAULT 1 COMMENT '状态',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典数据表';

-- OAuth2 客户端表
CREATE TABLE IF NOT EXISTS `oauth2_registered_client` (
    `id` VARCHAR(128) PRIMARY KEY,
    `client_id` VARCHAR(128) NOT NULL,
    `client_id_issued_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `client_secret` VARCHAR(256),
    `client_name` VARCHAR(256),
    `authorization_grant_types` VARCHAR(1000),
    `redirect_uris` VARCHAR(1000),
    `scopes` VARCHAR(1000),
    `client_settings` VARCHAR(2000),
    `token_settings` VARCHAR(2000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='OAuth2客户端表';

-- OAuth2 授权表
CREATE TABLE IF NOT EXISTS `oauth2_authorization` (
    `id` VARCHAR(128) PRIMARY KEY,
    `registered_client_id` VARCHAR(128) NOT NULL,
    `principal_name` VARCHAR(256),
    `authorization_grant_type` VARCHAR(100),
    `authorized_scopes` VARCHAR(1000),
    `attributes` VARCHAR(4000),
    `state` VARCHAR(500),
    `authorization_code_value` VARCHAR(4000),
    `access_token_value` VARCHAR(4000),
    `access_token_issued_at` TIMESTAMP,
    `access_token_expires_at` TIMESTAMP,
    `refresh_token_value` VARCHAR(4000),
    `refresh_token_issued_at` TIMESTAMP,
    `refresh_token_expires_at` TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='OAuth2授权表';

-- OAuth2 授权同意表
CREATE TABLE IF NOT EXISTS `oauth2_authorization_consent` (
    `registered_client_id` VARCHAR(128) NOT NULL,
    `principal_name` VARCHAR(256) NOT NULL,
    `authorities` VARCHAR(1000),
    PRIMARY KEY (`registered_client_id`, `principal_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='OAuth2授权同意表';

-- 数据源配置表
CREATE TABLE IF NOT EXISTS `gen_datasource` (
    `id` BIGINT PRIMARY KEY COMMENT '数据源ID',
    `name` VARCHAR(128) COMMENT '数据源名称',
    `type` VARCHAR(32) COMMENT '数据源类型',
    `url` VARCHAR(512) COMMENT '连接地址',
    `username` VARCHAR(64) COMMENT '用户名',
    `password` VARCHAR(128) COMMENT '密码',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据源配置表';

-- 代码生成配置表
CREATE TABLE IF NOT EXISTS `gen_config` (
    `id` BIGINT PRIMARY KEY COMMENT '配置ID',
    `table_name` VARCHAR(128) COMMENT '表名',
    `module_name` VARCHAR(64) COMMENT '模块名',
    `package_name` VARCHAR(128) COMMENT '包名',
    `entity_name` VARCHAR(64) COMMENT '实体类名',
    `author` VARCHAR(64) COMMENT '作者',
    `tpl_type` TINYINT DEFAULT 0 COMMENT '模板类型（0默认 1自定义）',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='代码生成配置表';

-- 文件存储配置表
CREATE TABLE IF NOT EXISTS `file_storage_config` (
    `id` BIGINT PRIMARY KEY COMMENT '配置ID',
    `storage_type` VARCHAR(32) COMMENT '存储类型（minio/oss/cos）',
    `config_name` VARCHAR(128) COMMENT '配置名称',
    `endpoint` VARCHAR(255) COMMENT '端点地址',
    `access_key` VARCHAR(255) COMMENT '访问密钥',
    `secret_key` VARCHAR(255) COMMENT '密钥',
    `bucket_name` VARCHAR(128) COMMENT '存储桶名称',
    `domain` VARCHAR(255) COMMENT '访问域名',
    `is_default` TINYINT DEFAULT 0 COMMENT '是否默认',
    `status` TINYINT DEFAULT 1 COMMENT '状态',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件存储配置表';

-- 文件记录表
CREATE TABLE IF NOT EXISTS `file_record` (
    `id` BIGINT PRIMARY KEY COMMENT '文件ID',
    `file_name` VARCHAR(255) COMMENT '文件名',
    `original_name` VARCHAR(255) COMMENT '原始文件名',
    `file_path` VARCHAR(512) COMMENT '文件路径',
    `file_url` VARCHAR(512) COMMENT '访问URL',
    `file_size` BIGINT COMMENT '文件大小（字节）',
    `file_type` VARCHAR(64) COMMENT '文件类型',
    `storage_type` VARCHAR(32) COMMENT '存储类型',
    `storage_config_id` BIGINT COMMENT '存储配置ID',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件记录表';

-- 初始化管理员用户（密码：admin123）
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `status`, `create_by`, `create_time`, `deleted`, `version`) VALUES
(1, 'admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '管理员', 1, 'system', NOW(), 0, 0);

-- 初始化角色
INSERT INTO `sys_role` (`id`, `role_name`, `role_key`, `sort`, `status`, `create_by`, `create_time`, `deleted`, `version`) VALUES
(1, '超级管理员', 'admin', 1, 1, 'system', NOW(), 0, 0),
(2, '普通角色', 'common', 2, 1, 'system', NOW(), 0, 0);

-- 初始化用户角色关联
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (1, 1);

-- 初始化菜单
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `sort`, `path`, `component`, `menu_type`, `perms`, `icon`, `status`, `create_by`, `create_time`, `deleted`) VALUES
(1, '系统管理', 0, 1, '/system', NULL, 'M', NULL, 'system', 1, 'system', NOW(), 0),
(2, '用户管理', 1, 1, 'user', 'system/user/index', 'C', 'system:user:list', 'user', 1, 'system', NOW(), 0),
(3, '角色管理', 1, 2, 'role', 'system/role/index', 'C', 'system:role:list', 'peoples', 1, 'system', NOW(), 0),
(4, '菜单管理', 1, 3, 'menu', 'system/menu/index', 'C', 'system:menu:list', 'tree-table', 1, 'system', NOW(), 0),
(5, '字典管理', 1, 4, 'dict', 'system/dict/index', 'C', 'system:dict:list', 'dict', 1, 'system', NOW(), 0),
(100, '用户查询', 2, 1, '', '', 'F', 'system:user:query', '#', 1, 'system', NOW(), 0),
(101, '用户新增', 2, 2, '', '', 'F', 'system:user:add', '#', 1, 'system', NOW(), 0),
(102, '用户修改', 2, 3, '', '', 'F', 'system:user:edit', '#', 1, 'system', NOW(), 0),
(103, '用户删除', 2, 4, '', '', 'F', 'system:user:remove', '#', 1, 'system', NOW(), 0),
(200, '角色查询', 3, 1, '', '', 'F', 'system:role:query', '#', 1, 'system', NOW(), 0),
(201, '角色新增', 3, 2, '', '', 'F', 'system:role:add', '#', 1, 'system', NOW(), 0),
(202, '角色修改', 3, 3, '', '', 'F', 'system:role:edit', '#', 1, 'system', NOW(), 0),
(203, '角色删除', 3, 4, '', '', 'F', 'system:role:remove', '#', 1, 'system', NOW(), 0);

-- 初始化字典类型
INSERT INTO `sys_dict_type` (`id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `deleted`) VALUES
(1, '用户性别', 'sys_user_sex', 1, 'system', NOW(), 0),
(2, '系统状态', 'sys_normal_disable', 1, 'system', NOW(), 0),
(3, '菜单类型', 'sys_menu_type', 1, 'system', NOW(), 0);

-- 初始化字典数据
INSERT INTO `sys_dict_data` (`id`, `dict_type`, `dict_label`, `dict_value`, `sort`, `status`, `create_by`, `create_time`, `deleted`) VALUES
(1, 'sys_user_sex', '男', '0', 1, 1, 'system', NOW(), 0),
(2, 'sys_user_sex', '女', '1', 2, 1, 'system', NOW(), 0),
(3, 'sys_normal_disable', '正常', '0', 1, 1, 'system', NOW(), 0),
(4, 'sys_normal_disable', '停用', '1', 2, 1, 'system', NOW(), 0),
(5, 'sys_menu_type', '目录', 'M', 1, 1, 'system', NOW(), 0),
(6, 'sys_menu_type', '菜单', 'C', 2, 1, 'system', NOW(), 0),
(7, 'sys_menu_type', '按钮', 'F', 3, 1, 'system', NOW(), 0);
