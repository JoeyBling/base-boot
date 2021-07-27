package com.tynet.saas.common.service.decorator;

import com.tynet.saas.common.service.IResponse;

/**
 * 基于{@link IResponse}日志装饰器实现
 *
 * @author Created by 思伟 on 2021/3/24
 */
public class LoggingResponseDecorator<T> extends AbstractResponseDecorator<T> {
    private static final long serialVersionUID = 1L;

    /**
     * one public constructor
     *
     * @param target 要装饰的目标对象
     */
    public LoggingResponseDecorator(IResponse<T> target) {
        super(target);
    }

    @Override
    public IResponse<T> getTarget() {
        logger.debug("返回此装饰器委托的真实目标对象.");
        return super.getTarget();
        // return new LoggingResponseDecorator<>(super.getTarget()).getTarget();
    }
}
