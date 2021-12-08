package com.tynet.saas.common.util.retry;

import com.tynet.saas.common.util.ExecutorUtil;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * 周期重试机制
 *
 * @author Created by 思伟 on 2021/8/20
 */
public class PeriodRetryStrategy implements IRetryStrategyAble {

    /**
     * one public constructor
     *
     * @param delayRetryPeriod 延时重试频率
     */
    public PeriodRetryStrategy(List<Duration> delayRetryPeriod) {
        this.delayRetryPeriod = Collections.synchronizedList(delayRetryPeriod);
    }

    /**
     * 延时重试频率
     */
    private List<Duration> delayRetryPeriod;

    /**
     * 重试次数
     */
    private int retryCount = 0;

    @Override
    public boolean canRetry() {
        return retryCount < delayRetryPeriod.size();
    }

    @Override
    public void retry() {
        ++retryCount;
        // 等待下一次尝试
        waitUntilNextTry();
    }

    /**
     * 等待下一次尝试
     */
    protected void waitUntilNextTry() {
        ExecutorUtil.sleep(
                // 此处下标索引需 - 1
                delayRetryPeriod.get(retryCount - 1)
        );
    }
}
