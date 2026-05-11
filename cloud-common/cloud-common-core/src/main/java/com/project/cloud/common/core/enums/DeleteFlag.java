package com.project.cloud.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 删除标志
 */
@Getter
@AllArgsConstructor
public enum DeleteFlag {

    /**
     * 正常
     */
    NORMAL(0, "正常"),

    /**
     * 已删除
     */
    DELETED(1, "已删除");

    private final int code;
    private final String info;
}
