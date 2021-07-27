package com.tynet.saas.common.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基础错误代码
 *
 * @author Created by 思伟 on 2020/5/6
 */
public class ResponseCodeConst {

    /**
     * 成功
     */
    public final static String SUCCESS = "0";

    /*----- 通用错误代码定义 start -----*/

    /**
     * 错误-系统原因
     */
    public final static String ERROR_SYSTEM = "-1";

    /**
     * 错误-校验不通过
     */
    public final static String ERROR_VALIDATE = "-2";

    /*----- 通用错误代码定义 end -----*/

    /********** 系统基础异常代码 **********/
    /**
     * 请求重复
     */
    public final static String ERROR_REPEAT_REQUEST = "00000099";

    /**
     * 错误代码消息集合
     */
    public static final Map<String, String> ERR_MSG_MAP = new LinkedHashMap<String, String>();

    static {
        ERR_MSG_MAP.put(ERROR_SYSTEM, "系统错误");
        ERR_MSG_MAP.put(ERROR_VALIDATE, "数据验证不通过");
        ERR_MSG_MAP.put(ERROR_REPEAT_REQUEST, "请求重复");
    }
}
