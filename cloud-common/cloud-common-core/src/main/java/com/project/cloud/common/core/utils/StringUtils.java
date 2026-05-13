package com.project.cloud.common.core.utils;

import cn.hutool.core.util.StrUtil;

public class StringUtils extends StrUtil {

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String nvl(String value, String defaultValue) {
        return isEmpty(value) ? defaultValue : value;
    }
}
