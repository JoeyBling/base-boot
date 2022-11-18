package com.tynet.test;

import com.tynet.base.BaseAppBootMvcTest;
import com.tynet.saas.common.util.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 自定义测试类
 *
 * @author Created by 思伟 on 2021/12/2
 */
public class MyAppTest extends BaseAppBootMvcTest {

    /**
     * Simple Test
     */
    @Test
    public void test() throws Exception {
        Assertions.assertTrue(true);
    }

    /**
     * 基础测试
     */
    @Test
    public void baseTest() {
        /**
         * JVM虚拟机状态
         */
        System.out.println(
                StringUtils.format("最大内存: {}m，已分配内存: {}m，已分配内存中的剩余空间: {}m，最大可用内存: {}m",
                        Runtime.getRuntime().maxMemory() >> 20,
                        Runtime.getRuntime().totalMemory() >> 20,
                        Runtime.getRuntime().freeMemory() >> 20,
                        (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) >> 20));

    }

    /**
     * just test
     */
    public static void main(String[] args) {
        // 应用目录（路径）
        final String appHome = System.getProperty("app.home");
        System.out.println(appHome);
    }

}
