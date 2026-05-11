package com.project.cloud.file.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件记录 VO
 */
@Data
@Schema(description = "文件记录信息")
public class FileRecordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文件ID")
    private Long id;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "原始文件名")
    private String originalName;

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "访问URL")
    private String fileUrl;

    @Schema(description = "文件大小（字节）")
    private Long fileSize;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "存储类型")
    private String storageType;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
