package com.project.cloud.file.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("file_record")
public class FileRecord extends BaseEntity {

    private String fileName;
    private String originalName;
    private String filePath;
    private String fileUrl;
    private Long fileSize;
    private String fileType;
    private String storageType;
}
