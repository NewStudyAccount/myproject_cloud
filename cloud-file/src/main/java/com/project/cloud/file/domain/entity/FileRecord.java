package com.project.cloud.file.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.project.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("file_record")
public class FileRecord extends BaseEntity {

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 原始文件名
     */
    @TableField("original_name")
    private String originalName;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 访问URL
     */
    @TableField("file_url")
    private String fileUrl;

    /**
     * 文件大小（字节）
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 存储类型
     */
    @TableField("storage_type")
    private String storageType;

    /**
     * 存储配置ID
     */
    @TableField("storage_config_id")
    private Long storageConfigId;
}
