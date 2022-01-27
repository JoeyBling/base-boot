package com.tynet.saas.common.util;

import com.tynet.saas.common.constant.SystemConst;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * 日期处理
 *
 * @author Created by 思伟 on 2020/10/29
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 默认时区
     */
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone(SystemConst.DEFAULT_TIME_ZONE_ID);

    /**
     * 默认时区ID
     */
    public static final ZoneId DEFAULT_ZONE_ID = DEFAULT_TIME_ZONE.toZoneId();

    /**
     * 中国上海时区
     */
    public static final ZoneOffset DEFAULT_ZONE_OFFSET = ZoneOffset.ofHours(8);

    /**
     * 日期格式(yyyy-MM-dd)
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式(HH:mm:ss)
     */
    public static final String TIME_PATTERN = "HH:mm:ss";

    /**
     * 日期时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准日期格式
     */
    public static final DateTimeFormatter NORM_DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * 标准时间格式
     */
    public static final DateTimeFormatter NORM_TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * 标准日期时间格式
     */
    public static final DateTimeFormatter NORM_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    /**
     * 当前时间
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime now() {
        return LocalDateTime.now(DEFAULT_ZONE_ID);
    }

    /**
     * 当前日期
     *
     * @return {@link LocalDate}
     */
    public static LocalDate nowDate() {
        return LocalDate.now(DEFAULT_ZONE_ID);
    }

    /**
     * 当前时间
     *
     * @return {@link LocalTime}
     */
    public static LocalTime nowTime() {
        return LocalTime.now(DEFAULT_ZONE_ID);
    }

    /**
     * 根据日期获取时间戳
     *
     * @param dateTime 时间
     * @return long
     */
    public static long getTimestamp(@NotNull LocalDateTime dateTime) {
        /**
         * {@link java.time.Instant#toEpochMilli()} - 毫秒数
         * {@link java.time.Instant#getEpochSecond()} - 秒数
         */
        return dateTime.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
        // return dateTime.toInstant(DEFAULT_ZONE_OFFSET).toEpochMilli();
    }

    /**
     * 根据日期获取时间戳
     *
     * @param date 日期
     * @return long
     */
    public static long getTimestamp(@NotNull LocalDate date) {
        return getTimestamp(date.atStartOfDay());
    }

    /**
     * 获取当前时间戳(毫秒级别)
     *
     * @return long
     */
    public static long currentTimestamp() {
        return getTimestamp(now());
    }

    /**
     * 获取当前时间戳(秒级别)
     *
     * @return 当前时间戳
     */
    public static long currentSecondTimestamp() {
        return currentTimestamp() / 1000;
    }

    /**
     * 毫秒转时间
     *
     * @param epochMilli 毫秒
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(long epochMilli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), DEFAULT_ZONE_ID);
    }

    /**
     * 格式化日期
     */
    public static String format(Date date) {
        return format(date, DATE_TIME_PATTERN);
    }

    /**
     * 格式化日期
     *
     * @param date    日期
     * @param pattern 格式
     * @return String
     */
    public static String format(Date date, String pattern) {
        final DateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setTimeZone(DEFAULT_TIME_ZONE);
        return dateFormat.format(date);
    }

    /**
     * 格式化日期
     */
    public static String format(LocalDateTime localDateTime) {
        return localDateTime.format(NORM_DATE_TIME_FORMATTER);
    }

    /**
     * 格式化日期
     */
    public static String format(LocalDate localDate) {
        return localDate.format(NORM_DATE_FORMATTER);
    }

    /**
     * 格式化日期
     */
    public static String format(LocalTime localTime) {
        return localTime.format(NORM_TIME_FORMATTER);
    }

    /**
     * 秒转毫秒
     *
     * @param second 秒
     * @return 毫秒
     */
    public static long transToMillisecond(int second) {
        return TimeUnit.MILLISECONDS.convert(second, TimeUnit.SECONDS);
        // return second * 1000;
    }

}
