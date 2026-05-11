package com.project.cloud.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态
 */
@Getter
@AllArgsConstructor
public enum UserStatus {

    /**
     * 正常
     */
    NORMAL(0, "正常"),

    /**
     * 停用
     */
    DISABLE(1, "停用");

    private final int code;
    private final String info;
}
