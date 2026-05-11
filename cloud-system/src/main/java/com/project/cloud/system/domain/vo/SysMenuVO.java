package com.project.cloud.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单 VO
 */
@Data
@Schema(description = "菜单信息")
public class SysMenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单ID")
    private Long id;

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

    @Schema(description = "类型（M目录 C菜单 F按钮）")
    private String menuType;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "子菜单列表")
    private List<SysMenuVO> children;
}
