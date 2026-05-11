package com.project.cloud.system.domain.query;

import com.project.cloud.common.core.domain.model.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "字典数据查询参数")
public class SysDictDataQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "字典标签")
    private String dictLabel;

    @Schema(description = "状态")
    private Integer status;
}
