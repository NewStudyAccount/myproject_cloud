package com.project.cloud.common.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 */
public class DateUtils {

    public static final String YYYY = "yyyy";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

    /**
     * 获取当前日期
     */
    public static String getDate() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

    /**
     * 获取当前时间
     */
    public static String getTime() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    /**
     * 格式化日期
     */
    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DATE_FORMATTER);
    }

    /**
     * 格式化时间
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DATETIME_FORMATTER);
    }

    /**
     * 解析日期
     */
    public static LocalDateTime parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * 解析时间
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    private DateUtils() {
    }
}
