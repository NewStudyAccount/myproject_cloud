package com.project.cloud.common.core.enums;

public enum DeleteFlag {

    NOT_DELETED(0, "未删除"),
    DELETED(1, "已删除");

    private final int code;
    private final String description;

    DeleteFlag(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
