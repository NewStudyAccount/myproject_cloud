package com.project.cloud.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色 VO
 */
@Data
@Schema(description = "角色信息")
public class SysRoleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID")
    private Long id;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色权限字符串")
    private String roleKey;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "菜单ID列表")
    private List<Long> menuIds;
}
