-- Project Cloud 数据库初始化脚本

CREATE DATABASE IF NOT EXISTS `project_cloud` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `project_cloud`;

-- 用户表
CREATE TABLE IF NOT EXISTS `sys_user` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(64) NOT NULL COMMENT '用户名',
    `password` VARCHAR(128) NOT NULL COMMENT '密码',
    `nickname` VARCHAR(64) COMMENT '昵称',
    `email` VARCHAR(128) COMMENT '邮箱',
    `phone` VARCHAR(20) COMMENT '手机号',
    `avatar` VARCHAR(255) COMMENT '头像',
    `status` TINYINT DEFAULT 0 COMMENT '状态(0正常 1停用)',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志(0未删除 1已删除)',
    `version` INT DEFAULT 0 COMMENT '版本号',
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS `sys_role` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `role_name` VARCHAR(64) NOT NULL COMMENT '角色名称',
    `role_key` VARCHAR(64) NOT NULL COMMENT '角色标识',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `status` TINYINT DEFAULT 0 COMMENT '状态(0正常 1停用)',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志',
    `version` INT DEFAULT 0 COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 菜单表
CREATE TABLE IF NOT EXISTS `sys_menu` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `menu_name` VARCHAR(64) NOT NULL COMMENT '菜单名称',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父菜单ID',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `path` VARCHAR(255) COMMENT '路由地址',
    `component` VARCHAR(255) COMMENT '组件路径',
    `menu_type` CHAR(1) COMMENT '菜单类型(M目录 C菜单 F按钮)',
    `perms` VARCHAR(128) COMMENT '权限标识',
    `icon` VARCHAR(128) COMMENT '图标',
    `status` TINYINT DEFAULT 0 COMMENT '状态(0正常 1停用)',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 字典类型表
CREATE TABLE IF NOT EXISTS `sys_dict_type` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `dict_name` VARCHAR(128) NOT NULL COMMENT '字典名称',
    `dict_type` VARCHAR(128) NOT NULL COMMENT '字典类型',
    `status` TINYINT DEFAULT 0 COMMENT '状态(0正常 1停用)',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志',
    UNIQUE KEY `uk_dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- 字典数据表
CREATE TABLE IF NOT EXISTS `sys_dict_data` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `dict_type` VARCHAR(128) NOT NULL COMMENT '字典类型',
    `dict_label` VARCHAR(128) NOT NULL COMMENT '字典标签',
    `dict_value` VARCHAR(128) NOT NULL COMMENT '字典值',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `status` TINYINT DEFAULT 0 COMMENT '状态(0正常 1停用)',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- 代码生成-数据源表
CREATE TABLE IF NOT EXISTS `gen_datasource` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(128) NOT NULL COMMENT '数据源名称',
    `url` VARCHAR(500) NOT NULL COMMENT '连接地址',
    `username` VARCHAR(64) NOT NULL COMMENT '用户名',
    `password` VARCHAR(128) NOT NULL COMMENT '密码',
    `db_type` VARCHAR(20) DEFAULT 'mysql' COMMENT '数据库类型',
    `status` TINYINT DEFAULT 0 COMMENT '状态',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志',
    `version` INT DEFAULT 0 COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据源表';

-- 代码生成-配置表
CREATE TABLE IF NOT EXISTS `gen_config` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `table_name` VARCHAR(128) NOT NULL COMMENT '表名',
    `table_comment` VARCHAR(255) COMMENT '表描述',
    `module_name` VARCHAR(64) COMMENT '模块名',
    `function_name` VARCHAR(128) COMMENT '功能名',
    `package_name` VARCHAR(128) COMMENT '包名',
    `author` VARCHAR(64) COMMENT '作者',
    `status` TINYINT DEFAULT 0 COMMENT '状态',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志',
    `version` INT DEFAULT 0 COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成配置表';

-- 文件记录表
CREATE TABLE IF NOT EXISTS `file_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `file_name` VARCHAR(255) COMMENT '文件名',
    `original_name` VARCHAR(255) COMMENT '原始文件名',
    `file_path` VARCHAR(500) COMMENT '文件路径',
    `file_url` VARCHAR(500) COMMENT '文件URL',
    `file_size` BIGINT COMMENT '文件大小',
    `file_type` VARCHAR(128) COMMENT '文件类型',
    `storage_type` VARCHAR(20) COMMENT '存储类型',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标志',
    `version` INT DEFAULT 0 COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件记录表';

-- 初始化管理员用户(密码: 123456)
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `status`) VALUES
(1, 'admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '管理员', 0);

-- 初始化角色
INSERT INTO `sys_role` (`id`, `role_name`, `role_key`, `sort`, `status`) VALUES
(1, '超级管理员', 'admin', 1, 0),
(2, '普通角色', 'common', 2, 0);
