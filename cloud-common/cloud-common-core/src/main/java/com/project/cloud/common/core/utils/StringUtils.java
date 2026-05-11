package com.project.cloud.common.core.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 字符串工具类
 */
public class StringUtils extends StrUtil {

    /**
     * 判断是否为空或空白
     */
    public static boolean isBlank(String str) {
        return StrUtil.isBlank(str);
    }

    /**
     * 判断是否不为空且不为空白
     */
    public static boolean isNotBlank(String str) {
        return StrUtil.isNotBlank(str);
    }

    /**
     * 判断是否为空
     */
    public static boolean isEmpty(String str) {
        return StrUtil.isEmpty(str);
    }

    /**
     * 判断是否不为空
     */
    public static boolean isNotEmpty(String str) {
        return StrUtil.isNotEmpty(str);
    }

    /**
     * 截取字符串
     */
    public static String substring(String str, int start, int end) {
        return StrUtil.sub(str, start, end);
    }

    /**
     * 格式化文本
     */
    public static String format(String template, Object... params) {
        return StrUtil.format(template, params);
    }

    /**
     * 驼峰转下划线
     */
    public static String toUnderScoreCase(String str) {
        return StrUtil.toUnderCase(str);
    }

    /**
     * 下划线转驼峰
     */
    public static String toCamelCase(String str) {
        return StrUtil.toCamelCase(str);
    }

    /**
     * 首字母大写
     */
    public static String capitalize(String str) {
        return StrUtil.upperFirst(str);
    }

    /**
     * 首字母小写
     */
    public static String unCapitalize(String str) {
        return StrUtil.lowerFirst(str);
    }

    private StringUtils() {
    }
}
