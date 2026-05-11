package com.project.cloud.system.domain.query;

import com.project.cloud.common.core.domain.model.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户查询参数")
public class SysUserQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "状态（0停用 1正常）")
    private Integer status;
}
