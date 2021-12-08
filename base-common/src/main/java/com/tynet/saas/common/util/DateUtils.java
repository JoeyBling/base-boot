package com.tynet.saas.common.util;

import com.tynet.saas.common.constant.SystemConst;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
     * 日期时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

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
     * 获取当前时间戳(毫秒级别)
     *
     * @return 当前时间戳
     */
    public static long currentTimeStamp() {
        // 中国上海时区
        return LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * 秒转毫秒
     *
     * @param second 秒
     * @return 毫秒
     */
    public static long transMilliSecond(int second) {
        return TimeUnit.MILLISECONDS.convert(second, TimeUnit.SECONDS);
        // return second * 1000;
    }
}
