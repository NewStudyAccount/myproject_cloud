package com.project.cloud.generator.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成配置 DTO
 */
@Data
@Schema(description = "代码生成配置DTO")
public class GenConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "配置ID")
    private Long id;

    @NotBlank(message = "表名不能为空")
    @Schema(description = "表名")
    private String tableName;

    @NotBlank(message = "模块名不能为空")
    @Schema(description = "模块名")
    private String moduleName;

    @NotBlank(message = "包名不能为空")
    @Schema(description = "包名")
    private String packageName;

    @NotBlank(message = "实体类名不能为空")
    @Schema(description = "实体类名")
    private String entityName;

    @Schema(description = "作者")
    private String author;

    @Schema(description = "模板类型")
    private Integer tplType;
}
