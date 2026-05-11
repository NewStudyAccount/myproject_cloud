package com.project.cloud.file.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件存储配置 VO
 */
@Data
@Schema(description = "文件存储配置信息")
public class FileStorageConfigVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "配置ID")
    private Long id;

    @Schema(description = "存储类型")
    private String storageType;

    @Schema(description = "配置名称")
    private String configName;

    @Schema(description = "端点地址")
    private String endpoint;

    @Schema(description = "访问密钥")
    private String accessKey;

    @Schema(description = "存储桶名称")
    private String bucketName;

    @Schema(description = "访问域名")
    private String domain;

    @Schema(description = "是否默认")
    private Integer isDefault;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
