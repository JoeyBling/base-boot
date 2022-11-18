package com.tynet.test;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReUtil;
import org.apache.commons.lang3.RegExUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证器（正则）测试
 *
 * @author Created by 思伟 on 2022/5/18
 */
public class ValidatorTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 基础测试
     */
    @Test
    public void baseTest() {
    }

    /**
     * just test
     */
    public static void main(String[] args) {
        /**
         * 中文姓名
         * <p>支持维吾尔族姓名（自版本`5.8.0`起支持 - https://gitee.com/dromara/hutool/pulls/592）
         */
        System.out.println(Validator.isChineseName("周思伟"));
        // 生僻字 - https://gitee.com/dromara/hutool/pulls/599
        System.out.println(Validator.isChineseName("刘欣䶮"));
        System.out.println(Validator.isChineseName("那吾克热·玉素甫江"));
        System.out.println(Validator.isChineseName("帕孜丽亚·帕塔尔"));
        System.out.println(Validator.isChineseName("阿提古丽·阿布力克木"));
        System.out.println(Validator.isChineseName("Jason Statham"));
    }

}
