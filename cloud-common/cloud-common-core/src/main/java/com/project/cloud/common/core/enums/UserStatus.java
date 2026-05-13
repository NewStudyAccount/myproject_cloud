package com.project.cloud.common.core.enums;

public enum UserStatus {

    ENABLE(0, "正常"),
    DISABLE(1, "停用");

    private final int code;
    private final String description;

    UserStatus(int code, String description) {
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
