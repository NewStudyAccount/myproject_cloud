package com.project.cloud.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_type")
public class SysDictType extends BaseEntity {

    private String dictName;
    private String dictType;
    private Integer status;
    private String remark;
}
