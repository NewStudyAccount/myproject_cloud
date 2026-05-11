package com.project.cloud.generator.domain.query;

import com.project.cloud.common.core.domain.model.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成配置查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "代码生成配置查询参数")
public class GenConfigQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    @Schema(description = "表名")
    private String tableName;

    @Schema(description = "模块名")
    private String moduleName;

    @Schema(description = "实体类名")
    private String entityName;
}
