package com.tynet.test;

import com.tynet.saas.common.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * 简单测试
 *
 * @author Created by 思伟 on 2022/1/13
 */
public class SimpleTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 基础测试
     */
    @Test
    public void baseTest() {
        // 当前时间戳
        final long currentTimestamp = DateUtils.currentTimestamp();
        logger.debug("{}-{}-{}", currentTimestamp,
                DateUtils.getTimestamp(DateUtils.nowDate()),
                DateUtils.of(currentTimestamp));
        logger.debug("{}-{}", Duration.ZERO, Duration.parse(Duration.ZERO.toString()));
    }

    /**
     * just test
     */
    public static void main(String[] args) {

    }

}
