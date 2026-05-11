package com.project.cloud.system.domain.query;

import com.project.cloud.common.core.domain.model.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色查询参数")
public class SysRoleQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色权限字符串")
    private String roleKey;

    @Schema(description = "状态")
    private Integer status;
}
