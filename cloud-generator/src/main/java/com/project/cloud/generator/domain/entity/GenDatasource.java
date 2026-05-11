package com.project.cloud.generator.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.project.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据源配置实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gen_datasource")
public class GenDatasource extends BaseEntity {

    /**
     * 数据源名称
     */
    @TableField("name")
    private String name;

    /**
     * 数据源类型
     */
    @TableField("type")
    private String type;

    /**
     * 连接地址
     */
    @TableField("url")
    private String url;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;
}
