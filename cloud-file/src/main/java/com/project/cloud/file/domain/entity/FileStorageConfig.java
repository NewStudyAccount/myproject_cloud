package com.project.cloud.file.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("file_storage_config")
public class FileStorageConfig extends BaseEntity {

    private String storageType;
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private Integer status;
    private String remark;
}
