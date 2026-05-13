package com.project.cloud.generator.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gen_config")
public class GenConfig extends BaseEntity {

    private String tableName;
    private String tableComment;
    private String moduleName;
    private String functionName;
    private String packageName;
    private String author;
    private Integer status;
}
