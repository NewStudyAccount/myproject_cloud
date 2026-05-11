package com.project.cloud.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色 DTO
 */
@Data
@Schema(description = "角色DTO")
public class SysRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID")
    private Long id;

    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称")
    private String roleName;

    @NotBlank(message = "角色权限字符串不能为空")
    @Schema(description = "角色权限字符串")
    private String roleKey;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "菜单ID列表")
    private List<Long> menuIds;
}
