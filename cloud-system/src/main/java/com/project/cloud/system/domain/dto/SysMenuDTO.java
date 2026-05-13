package com.project.cloud.system.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysMenuDTO implements Serializable {

    private Long id;

    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    private Long parentId;

    private Integer sort;

    private String path;

    private String component;

    private String menuType;

    private String perms;

    private String icon;

    private Integer status;
}
