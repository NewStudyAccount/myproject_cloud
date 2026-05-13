package com.project.cloud.common.core.enums;

public enum DataScope {

    ALL("1", "全部数据权限"),
    CUSTOM("2", "自定义数据权限"),
    DEPT("3", "本部门数据权限"),
    DEPT_AND_CHILD("4", "本部门及以下数据权限"),
    SELF("5", "仅本人数据权限");

    private final String code;
    private final String description;

    DataScope(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
