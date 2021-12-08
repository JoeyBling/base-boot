package com.tynet.saas.common.util.retry;

import com.tynet.saas.common.util.ExecutorUtil;
import lombok.AllArgsConstructor;

import java.time.Duration;

/**
 * 间隔重试机制
 *
 * @author Created by 思伟 on 2021/8/20
 */
@AllArgsConstructor
public class IntervalRetryStrategy implements IRetryStrategyAble {

    /**
     * 最大重试次数
     */
    private int maxRetries;

    /**
     * 间隔时间
     */
    private Duration intervalTime;

    @Override
    public boolean canRetry() {
        return maxRetries > 0;
    }

    @Override
    public void retry() {
        maxRetries--;
        // 等待下一次尝试
        waitUntilNextTry();
    }

    /**
     * 等待下一次尝试
     */
    protected void waitUntilNextTry() {
        ExecutorUtil.sleep(intervalTime);
    }
}
