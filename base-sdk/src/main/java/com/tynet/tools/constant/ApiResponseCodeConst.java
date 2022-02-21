package com.tynet.tools.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基础错误代码
 *
 * @author Created by 思伟 on 2020/5/6
 */
public class ApiResponseCodeConst {

    /**
     * 成功
     */
    public static final String SUCCESS = "0";

    /*----- 通用错误代码定义 start -----*/

    /**
     * 错误-系统原因
     */
    public static final String ERROR_SYSTEM = "-1";

    /**
     * 错误-校验不通过
     */
    public static final String ERROR_VALIDATE = "-2";

    /*----- 通用错误代码定义 end -----*/

    /********** 系统基础异常代码 **********/

    /**
     * 请求非法
     */
    public static final String ERROR_ILLEGAL_REQUEST = "00000001";

    /**
     * 请求数字签名不能为空
     */
    public static final String ERROR_NEED_SIGN = "00000005";

    /**
     * 数字签名校验错误
     */
    public static final String ERROR_SIGN = "00000006";

    /**
     * 请求重复
     */
    public static final String ERROR_REPEAT_REQUEST = "00000099";

    /**
     * 错误代码消息集合
     */
    public static final Map<String, String> ERR_MSG_MAP = new LinkedHashMap<>();

    static {
        ERR_MSG_MAP.put(ERROR_SYSTEM, "系统错误");
        ERR_MSG_MAP.put(ERROR_VALIDATE, "数据验证不通过");
        ERR_MSG_MAP.put(ERROR_ILLEGAL_REQUEST, "请求非法");
        ERR_MSG_MAP.put(ERROR_NEED_SIGN, "请求数字签名不能为空");
        ERR_MSG_MAP.put(ERROR_SIGN, "数字签名校验错误");
        ERR_MSG_MAP.put(ERROR_REPEAT_REQUEST, "请求重复");
    }

}
