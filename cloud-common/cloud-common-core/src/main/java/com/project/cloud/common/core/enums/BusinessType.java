package com.project.cloud.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务操作类型
 */
@Getter
@AllArgsConstructor
public enum BusinessType {

    /**
     * 其它
     */
    OTHER(0, "其它"),

    /**
     * 新增
     */
    INSERT(1, "新增"),

    /**
     * 修改
     */
    UPDATE(2, "修改"),

    /**
     * 删除
     */
    DELETE(3, "删除"),

    /**
     * 导出
     */
    EXPORT(4, "导出"),

    /**
     * 导入
     */
    IMPORT(5, "导入"),

    /**
     * 清空
     */
    CLEAN(6, "清空");

    private final int code;
    private final String info;
}
