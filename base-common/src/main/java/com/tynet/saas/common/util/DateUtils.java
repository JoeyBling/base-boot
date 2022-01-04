package com.tynet.saas.common.util;

import com.tynet.saas.common.constant.SystemConst;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
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
     * 获取当前时间戳(毫秒级别)
     *
     * @return long
     */
    public static long currentTimeStamp() {
        // 中国上海时区
        return LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * 获取当前时间戳(秒级别)
     *
     * @return 当前时间戳
     * @see #currentTimeStamp()
     */
    public static long currentSecondTimeStamp() {
        return currentTimeStamp() / 1000;
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
        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    /**
     * 格式化日期
     */
    public static String format(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    /**
     * 格式化日期
     */
    public static String format(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ofPattern(TIME_PATTERN));
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
