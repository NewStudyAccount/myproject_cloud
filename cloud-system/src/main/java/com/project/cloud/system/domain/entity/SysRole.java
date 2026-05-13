package com.project.cloud.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends BaseEntity {

    private String roleName;
    private String roleKey;
    private Integer sort;
    private Integer status;
}
