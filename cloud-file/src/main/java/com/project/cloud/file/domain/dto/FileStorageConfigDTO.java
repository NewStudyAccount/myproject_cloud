package com.project.cloud.file.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件存储配置 DTO
 */
@Data
@Schema(description = "文件存储配置DTO")
public class FileStorageConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "配置ID")
    private Long id;

    @NotBlank(message = "存储类型不能为空")
    @Schema(description = "存储类型（minio/oss/cos）")
    private String storageType;

    @NotBlank(message = "配置名称不能为空")
    @Schema(description = "配置名称")
    private String configName;

    @Schema(description = "端点地址")
    private String endpoint;

    @NotBlank(message = "访问密钥不能为空")
    @Schema(description = "访问密钥")
    private String accessKey;

    @NotBlank(message = "密钥不能为空")
    @Schema(description = "密钥")
    private String secretKey;

    @NotBlank(message = "存储桶名称不能为空")
    @Schema(description = "存储桶名称")
    private String bucketName;

    @Schema(description = "访问域名")
    private String domain;

    @Schema(description = "是否默认")
    private Integer isDefault;

    @Schema(description = "状态")
    private Integer status;
}
