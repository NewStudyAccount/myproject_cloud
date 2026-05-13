package com.project.cloud.common.core.enums;

public enum BusinessType {

    OTHER("其他"),
    INSERT("新增"),
    UPDATE("修改"),
    DELETE("删除"),
    EXPORT("导出"),
    IMPORT("导入"),
    GRANT("授权"),
    FORCE_LOGOUT("强退");

    private final String description;

    BusinessType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
