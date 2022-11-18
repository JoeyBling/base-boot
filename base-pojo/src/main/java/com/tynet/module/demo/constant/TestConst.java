package com.tynet.module.demo.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 测试常量
 *
 * @author Created by 思伟 on 2022/6/24
 */
@Deprecated
public class TestConst {
    /**
     * 测试
     */
    public static final String TEST = "TEST";

    /**
     * 常量集合
     */
    public static final Map<String, String> CONST_MAP = new LinkedHashMap<>();

    static {
        CONST_MAP.put(TEST, "测试");
    }

    /**
     * 校验变量是否合法
     *
     * @param var 变量
     * @return boolean
     */
    public static final boolean isValid(final String var) {
        return CONST_MAP.containsKey(var);
    }

}
