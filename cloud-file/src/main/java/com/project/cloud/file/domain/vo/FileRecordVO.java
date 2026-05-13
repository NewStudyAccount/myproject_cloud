package com.project.cloud.file.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FileRecordVO implements Serializable {

    private Long id;
    private String fileName;
    private String originalName;
    private String filePath;
    private String fileUrl;
    private Long fileSize;
    private String fileType;
    private String storageType;
    private LocalDateTime createTime;
}
