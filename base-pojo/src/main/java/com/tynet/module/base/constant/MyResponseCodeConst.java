package com.tynet.module.base.constant;

import com.tynet.saas.common.constant.ResponseCodeConst;
import org.springframework.stereotype.Service;

/**
 * 自定义错误代码
 * <p>
 * 参考：阿里巴巴 - 《Java开发手册》 - 错误码列表
 * </p>
 *
 * @author Created by 思伟 on 2021/1/15
 */
@Service
public final class MyResponseCodeConst extends ResponseCodeConst {

    /**
     * 合理用药模块全局异常
     */
    public static final String ERROR_RATIONAL_DRUG = "A8888";

    /**
     * 验证码模块全局异常
     */
    public static final String ERROR_CAPTCHA = "A0240";

    /**
     * 静态初始模块
     */
    static {
        ERR_MSG_MAP.put(ERROR_RATIONAL_DRUG, "合理用药检查异常");
        ERR_MSG_MAP.put(ERROR_CAPTCHA, "验证码异常");
    }

}
