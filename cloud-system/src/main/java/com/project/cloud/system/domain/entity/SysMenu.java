package com.project.cloud.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.project.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

    private String menuName;
    private Long parentId;
    private Integer sort;
    private String path;
    private String component;
    private String menuType;
    private String perms;
    private String icon;
    private Integer status;

    @TableField(exist = false)
    private List<SysMenu> children;
}
