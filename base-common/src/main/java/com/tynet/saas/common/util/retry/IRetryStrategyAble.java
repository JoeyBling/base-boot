package com.tynet.saas.common.util.retry;

/**
 * 重试机制(策略)能力接口
 *
 * @author Created by 思伟 on 2021/8/20
 */
public interface IRetryStrategyAble {

    /**
     * 是否可以重试
     *
     * @return boolean
     */
    boolean canRetry();

    /**
     * 进行重试
     */
    void retry();

}
