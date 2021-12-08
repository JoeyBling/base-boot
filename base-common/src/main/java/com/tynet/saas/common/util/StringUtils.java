package com.tynet.saas.common.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

/**
 * 字符串工具类
 *
 * @author Created by 思伟 on 2021/2/22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 将对象转换为String
     *
     * @param value Object
     * @return String
     */
    public static String toString(Object value) {
        String strValue;
        if (value == null) {
            strValue = null;
        } else if (value instanceof String) {
            strValue = (String) value;
        } else if (value instanceof BigDecimal) {
            strValue = ((BigDecimal) value).toString();
        } else if (value instanceof Integer) {
            strValue = ((Integer) value).toString();
        } else if (value instanceof Long) {
            strValue = ((Long) value).toString();
        } else if (value instanceof Float) {
            strValue = ((Float) value).toString();
        } else if (value instanceof Double) {
            strValue = ((Double) value).toString();
        } else if (value instanceof Boolean) {
            strValue = ((Boolean) value).toString();
        } else if (value instanceof Date) {
            DateFormat format = new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN);
            format.setTimeZone(DateUtils.DEFAULT_TIME_ZONE);
            strValue = format.format((Date) value);
        } else if (value.getClass().isArray()) {
            strValue = Arrays.toString((Object[]) value);
        } else {
            strValue = value.toString();
        }
        return strValue;
    }

    /**
     * message格式化，用{}替代任何的对象
     * 参考:{@link cn.hutool.core.util.StrUtil#format(CharSequence, Object...)}
     *
     * @param message 待格式化字符串
     * @param objs    入参
     * @return 格式化后的字符串
     */
    public static String format(String message, Object... objs) {
        for (Object obj : objs) {
            message = formatByStr(message, obj, "{}");
        }
        return message;
    }

    /**
     * 使用指定的替换符进行格式化字符串
     *
     * @param str       待格式化字符串
     * @param obj       入参
     * @param formatStr 格式化替换字符串(默认:{})
     * @return 格式化后的字符串
     */
    public static String formatByStr(String str, Object obj, String formatStr) {
        formatStr = defaultString(formatStr, "{}");
        // 获取开始下标
        int i = str.indexOf(formatStr);
        if (i != -1) {
            return str.substring(0, i).concat(stripToEmpty(toString(obj)))
                    .concat(str.substring(i + formatStr.length()));
        } else {
            // 跳过替换
            return str;
        }
    }

    /**
     * 转换为Integer类型
     *
     * @param cs 待转换字符串
     * @return Integer
     */
    public static Integer getIntegerValue(CharSequence cs) {
        // Integer.getInteger
        return isNumeric(cs) ? Integer.parseInt(cs.toString()) : null;
    }

    /**
     * 转换为Integer类型，当输入的字符串不是一个整型类型时返回默认值
     *
     * @param defaultInt 要返回的默认值
     *                   if the input is {@code null}, may be null
     * @see #getIntegerValue(CharSequence)
     */
    public static Integer getIntegerValue(CharSequence cs, Integer defaultInt) {
        final Integer intVal = getIntegerValue(cs);
        return intVal == null ? defaultInt : intVal;
    }

    /**
     * just test
     */
    public static void main(String[] args) {
        System.out.println(format("{}{}_JoeyBling_Blog:{}",
                new Date(), "周思伟", "https://zhousiwei.gitee.io/ibooks/"));
    }
}
