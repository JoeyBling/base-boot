package com.tynet.saas.common.service.decorator;

import com.tynet.saas.common.service.IResponse;

import java.util.Objects;

/**
 * 基于{@link IResponse}简单装饰器实现
 * TODO 考虑是否继承 {@link com.tynet.saas.common.service.adapter.IResponseAdapter}
 * 可以抽离抽象类 + #getTargetResponse
 * {@code targetResponse instanceof AbstractResponseDecorator ? AbstractResponseDecorator#getTargetResponse : targetResponse}
 *
 * @author Created by 思伟 on 2021/3/22
 * @deprecated 将来会废弃 {@link SimpleResponseDecorator}
 */
@Deprecated
public class SimpleResponseDecoratorV1<T> implements IResponse<T> {
    private static final long serialVersionUID = 1L;

    /**
     * 目标响应对象
     */
    private IResponse<T> targetResponse;

    /**
     * one public constructor
     *
     * @param targetResponse 要装饰的目标响应对象
     */
    public SimpleResponseDecoratorV1(IResponse<T> targetResponse) {
        this.targetResponse = Objects.requireNonNull(targetResponse);
    }

    @Override
    public boolean isSuccess() {
        return targetResponse.isSuccess();
    }

    @Override
    public String getErrMsg() {
        return targetResponse.getErrMsg();
    }

    @Override
    public T getData() {
        return targetResponse.getData();
    }

    /**
     * 返回此响应委托的目标响应对象.
     *
     * @return {@link #targetResponse}
     */
    public IResponse<T> getTargetResponse() {
        return targetResponse;
    }
}
