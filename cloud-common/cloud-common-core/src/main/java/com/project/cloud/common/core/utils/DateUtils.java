package com.project.cloud.common.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATETIME_FORMATTER) : null;
    }

    public static LocalDate parseDate(String dateStr) {
        return dateStr != null ? LocalDate.parse(dateStr, DATE_FORMATTER) : null;
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER) : null;
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return localDateTime != null
                ? Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
                : null;
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date != null
                ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                : null;
    }

    private DateUtils() {
    }
}
