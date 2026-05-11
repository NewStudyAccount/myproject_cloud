package com.project.cloud.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单 DTO
 */
@Data
@Schema(description = "菜单DTO")
public class SysMenuDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单ID")
    private Long id;

    @NotBlank(message = "菜单名称不能为空")
    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "父菜单ID")
    private Long parentId;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "路由路径")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @NotBlank(message = "菜单类型不能为空")
    @Schema(description = "类型（M目录 C菜单 F按钮）")
    private String menuType;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "状态")
    private Integer status;
}
