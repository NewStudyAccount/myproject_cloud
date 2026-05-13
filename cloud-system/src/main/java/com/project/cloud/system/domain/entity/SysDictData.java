package com.project.cloud.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_data")
public class SysDictData extends BaseEntity {

    private String dictType;
    private String dictLabel;
    private String dictValue;
    private Integer sort;
    private Integer status;
    private String remark;
}
