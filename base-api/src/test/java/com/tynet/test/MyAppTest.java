package com.tynet.test;

import com.tynet.base.BaseAppBootMvcTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 自定义测试类
 *
 * @author Created by 思伟 on 2021/12/2
 */
public class MyAppTest extends BaseAppBootMvcTest {

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Simple Test
     */
    @Test
    public void test() throws Exception {
        Assertions.assertTrue(true);
    }

}
