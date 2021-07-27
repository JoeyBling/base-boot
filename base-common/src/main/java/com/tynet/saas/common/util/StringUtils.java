package com.tynet.saas.common.util;

import java.math.BigDecimal;
import java.security.SecureRandom;
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
     * 日期时间格式(yyyy-MM-dd HH:mm:ss)
     */
    static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认时区
     */
    static String DATE_TIMEZONE = "GMT+8";

    /**
     * 数字字符数组
     */
    private final static char[] NUM_CHAR_ARR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    /**
     * 数字+大小写英文字符数组
     */
    private final static char[] STR_CHAR_ARR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

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
            DateFormat format = new SimpleDateFormat(DATE_TIME_PATTERN);
            format.setTimeZone(TimeZone.getTimeZone(DATE_TIMEZONE));
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
     * 生成随机密码带字符
     *
     * @param pwdLen 生成密码的总长度
     * @return 密码字符串
     */
    public static String genRandomStr(int pwdLen) {
        return getRandomStr(pwdLen, STR_CHAR_ARR);
    }

    /**
     * 生成纯数字随机密码
     *
     * @param pwdLen 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String genRandomForNumStr(int pwdLen) {
        return getRandomStr(pwdLen, NUM_CHAR_ARR);
    }

    /**
     * 指定字符数组生成指定长度的随机数
     *
     * @param pwdLen  随机数长度
     * @param charArr 字符数组
     * @return String
     */
    private static String getRandomStr(final int pwdLen, final char[] charArr) {
        // 生成的随机数下标
        int randomIndex;
        // 生成的密码的长度
        int count = 0;

        StringBuffer pwd = new StringBuffer(EMPTY);
        SecureRandom random = new SecureRandom();
        while (count < pwdLen) {
            /**
             * 生成随机数，取绝对值，防止生成负数
             * <p>
             * 随机返回一个值在[0-num]的int类型的整数,包括0不包括num
             * </p>
             */
            randomIndex = Math.abs(random.nextInt(charArr.length));
            if (randomIndex >= 0 && randomIndex < charArr.length) {
                pwd.append(charArr[randomIndex]);
                ++count;
            }
        }
        return pwd.toString();
    }

    /**
     * just test
     */
    public static void main(String[] args) {
        System.out.println(format("{}{}_JoeyBling_Blog:{}",
                new Date(), "周思伟", "https://zhousiwei.gitee.io/ibooks/"));
        for (int i = 0; i < 1000; i++) {
            System.out.println(genRandomForNumStr(4));
        }
    }
}
