package com.project.cloud.file.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.project.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件存储配置实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("file_storage_config")
public class FileStorageConfig extends BaseEntity {

    /**
     * 存储类型（minio/oss/cos）
     */
    @TableField("storage_type")
    private String storageType;

    /**
     * 配置名称
     */
    @TableField("config_name")
    private String configName;

    /**
     * 端点地址
     */
    @TableField("endpoint")
    private String endpoint;

    /**
     * 访问密钥
     */
    @TableField("access_key")
    private String accessKey;

    /**
     * 密钥
     */
    @TableField("secret_key")
    private String secretKey;

    /**
     * 存储桶名称
     */
    @TableField("bucket_name")
    private String bucketName;

    /**
     * 访问域名
     */
    @TableField("domain")
    private String domain;

    /**
     * 是否默认
     */
    @TableField("is_default")
    private Integer isDefault;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;
}
