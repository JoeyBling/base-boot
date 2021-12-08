package com.tynet.common.util;

import com.tynet.module.base.constant.SystemConst;

import java.math.BigDecimal;

/**
 * 数字工具类
 *
 * @author Created by 思伟 on 2020/4/15
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {
    /**
     * 默认BigDecimal计算舍入模式
     */
    public static final int ROUNDING_MODE = SystemConst.GLOBAL_BIG_DECIMAL_ROUNDING_MODE;

    /**
     * 将单位分转换成单位圆.
     *
     * @param fen 将要被转换为元的分的数值
     * @return the string
     */
    public static String fenToYuan(Integer fen) {
        return fenToYuan(new BigDecimal(fen)).toPlainString();
    }

    /**
     * 将单位分转换成单位圆.
     *
     * @param fen 将要被转换为元的分的数值
     * @return the BigDecimal
     */
    public static BigDecimal fenToYuan(BigDecimal fen) {
        return fen.divide(new BigDecimal(100))
                .setScale(2, ROUNDING_MODE);
    }

    /**
     * 将单位元转换为单位分.
     *
     * @param yuan 将要转换的元的数值字符串
     * @return the integer
     */
    public static Integer yuanToFen(String yuan) {
        return yuanToFen(new BigDecimal(yuan)).intValue();
    }

    /**
     * 将单位元转换为单位分.
     *
     * @param yuan 将要转换的元的数值字符串
     * @return the integer
     */
    public static BigDecimal yuanToFen(BigDecimal yuan) {
        return yuan.setScale(2, ROUNDING_MODE)
                .multiply(new BigDecimal(100));
    }

    /**
     * just test
     */
    public static void main(String[] args) {
        System.out.println(fenToYuan(new BigDecimal(10)));
        System.out.println(fenToYuan(10));
        System.out.println(yuanToFen("0.23"));
        System.out.println(yuanToFen(new BigDecimal(0.23)));
    }

}
