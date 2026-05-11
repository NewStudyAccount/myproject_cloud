package com.project.cloud.system.domain.query;

import com.project.cloud.common.core.domain.model.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "菜单查询参数")
public class SysMenuQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "菜单类型")
    private String menuType;
}
