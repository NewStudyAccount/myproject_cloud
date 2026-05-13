package com.project.cloud.system.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysMenuVO implements Serializable {

    private Long id;
    private String menuName;
    private Long parentId;
    private Integer sort;
    private String path;
    private String component;
    private String menuType;
    private String perms;
    private String icon;
    private Integer status;
    private List<SysMenuVO> children;
}
