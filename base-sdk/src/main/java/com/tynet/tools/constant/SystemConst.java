package com.tynet.tools.constant;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 全局系统常量
 *
 * @author Updated by 思伟 on 2019/8/9
 */
public class SystemConst {

    /**
     * 默认编码
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 默认时区ID
     * <p>
     * 缩写：GMT+8、完整：GMT+08:00
     * </p>
     */
    public static final String DEFAULT_TIME_ZONE_ID = "GMT+8";

    /**
     * 全局默认BigDecimal计算舍入模式
     *
     * @see BigDecimal#ROUND_UP 直接进位处理(tips:如果将要舍弃的位上的值是0，不做进位处理)
     * @see BigDecimal#ROUND_DOWN 直接舍弃处理
     * @see BigDecimal#ROUND_HALF_UP 四舍五入
     * @see BigDecimal#ROUND_HALF_DOWN 五舍六入
     * @see BigDecimal#setScale(int, int) 调用此方法建议使用此全局变量，防止出现无限循环小数
     * @see BigDecimal#divide(BigDecimal, int)
     */
    public static final int GLOBAL_BIG_DECIMAL_ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

}
