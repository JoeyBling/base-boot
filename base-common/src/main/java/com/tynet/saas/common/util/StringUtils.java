package com.tynet.saas.common.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;

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
            strValue = DateUtils.format((Date) value);
        } else if (value instanceof LocalDateTime) {
            strValue = DateUtils.format((LocalDateTime) value);
        } else if (value instanceof LocalDate) {
            strValue = DateUtils.format((LocalDate) value);
        } else if (value instanceof LocalTime) {
            strValue = DateUtils.format((LocalTime) value);
        } else if (value.getClass().isArray()) {
            // 基本数据类型处理...
            if (value instanceof char[]) {
                strValue = Arrays.toString((char[]) value);
            } else if (value instanceof long[]) {
                strValue = Arrays.toString((long[]) value);
            } else if (value instanceof int[]) {
                strValue = Arrays.toString((int[]) value);
            } else if (value instanceof short[]) {
                strValue = Arrays.toString((short[]) value);
            } else if (value instanceof byte[]) {
                strValue = Arrays.toString((byte[]) value);
            } else if (value instanceof boolean[]) {
                strValue = Arrays.toString((boolean[]) value);
            } else if (value instanceof float[]) {
                strValue = Arrays.toString((float[]) value);
            } else if (value instanceof double[]) {
                strValue = Arrays.toString((double[]) value);
            } else {
                // 对象数组
                strValue = Arrays.deepToString((Object[]) value);
            }
        } else {
            strValue = value.toString();
        }
        return strValue;
    }

    /**
     * message格式化，用{}替代任何的对象
     * 参考：{@link cn.hutool.core.util.StrUtil#format}
     *
     * @param message 待格式化字符串
     * @param objs    入参
     * @return 格式化后的字符串
     */
    public static String format(String message, Object... objs) {
        for (Object obj : objs) {
            message = formatWith(message, obj, "{}");
        }
        return message;
    }

    /**
     * 使用指定的替换符进行格式化字符串
     *
     * @param str         待格式化字符串
     * @param obj         入参
     * @param placeHolder 占位符<p>Defaults {@literal {}}.
     * @return 格式化后的字符串
     */
    public static String formatWith(String str, Object obj, String placeHolder) {
        placeHolder = defaultString(placeHolder, "{}");
        // 获取开始下标
        int i = str.indexOf(placeHolder);
        if (i != -1) {
            return str.substring(0, i).concat(stripToEmpty(toString(obj)))
                    .concat(str.substring(i + placeHolder.length()));
        } else {
            // 跳过替换
            return str;
        }
    }

    /**
     * 转换为{@link java.lang.Integer#TYPE}类型
     *
     * @param cs 待转换字符串
     * @return Integer
     */
    public static Integer getIntegerValue(CharSequence cs) {
        return isNumeric(cs) ? Integer.parseInt(cs.toString()) : null;
    }

    /**
     * 转换为{@link java.lang.Integer#TYPE}类型
     *
     * @param defaultInt 要返回的默认值
     *                   if the input is {@code null}, may be null
     */
    public static Integer getIntegerValue(CharSequence cs, Integer defaultInt) {
        final Integer intVal = getIntegerValue(cs);
        return intVal == null ? defaultInt : intVal;
    }

    /**
     * just test
     */
    public static void main(String[] args) {
        System.out.println(format("{}-{}_JoeyBling_Blog:{}",
                DateUtils.now(), "周思伟", "https://zhousiwei.gitee.io/ibooks/"));

        System.out.println(toString(new Date()));
        System.out.println(toString(DateUtils.now()));
        System.out.println(toString(DateUtils.nowDate()));
        System.out.println(toString(DateUtils.nowTime()));
        System.out.println(toString("just_test".toCharArray()));

        System.out.println(toString(DateUtils.of(new Date())));
        System.out.println(toString(DateUtils.toDate()));

    }

}
