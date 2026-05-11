package com.project.cloud.generator.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.project.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成配置实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gen_config")
public class GenConfig extends BaseEntity {

    /**
     * 表名
     */
    @TableField("table_name")
    private String tableName;

    /**
     * 模块名
     */
    @TableField("module_name")
    private String moduleName;

    /**
     * 包名
     */
    @TableField("package_name")
    private String packageName;

    /**
     * 实体类名
     */
    @TableField("entity_name")
    private String entityName;

    /**
     * 作者
     */
    @TableField("author")
    private String author;

    /**
     * 模板类型（0默认 1自定义）
     */
    @TableField("tpl_type")
    private Integer tplType;
}
