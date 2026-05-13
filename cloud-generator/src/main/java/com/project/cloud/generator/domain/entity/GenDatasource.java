package com.project.cloud.generator.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gen_datasource")
public class GenDatasource extends BaseEntity {

    private String name;
    private String url;
    private String username;
    private String password;
    private String dbType;
    private Integer status;
    private String remark;
}
