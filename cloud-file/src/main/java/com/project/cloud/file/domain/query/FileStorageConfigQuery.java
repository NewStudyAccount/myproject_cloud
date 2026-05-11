package com.project.cloud.file.domain.query;

import com.project.cloud.common.core.domain.model.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件存储配置查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文件存储配置查询参数")
public class FileStorageConfigQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    @Schema(description = "存储类型")
    private String storageType;

    @Schema(description = "配置名称")
    private String configName;

    @Schema(description = "状态")
    private Integer status;
}
