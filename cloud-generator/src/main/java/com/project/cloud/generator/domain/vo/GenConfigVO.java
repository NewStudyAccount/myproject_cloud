package com.project.cloud.generator.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 代码生成配置 VO
 */
@Data
@Schema(description = "代码生成配置信息")
public class GenConfigVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "配置ID")
    private Long id;

    @Schema(description = "表名")
    private String tableName;

    @Schema(description = "模块名")
    private String moduleName;

    @Schema(description = "包名")
    private String packageName;

    @Schema(description = "实体类名")
    private String entityName;

    @Schema(description = "作者")
    private String author;

    @Schema(description = "模板类型")
    private Integer tplType;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
