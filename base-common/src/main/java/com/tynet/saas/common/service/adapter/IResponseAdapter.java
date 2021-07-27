package com.tynet.saas.common.service.adapter;

import com.tynet.saas.common.service.IResponse;

/**
 * 允许带有空方法的{@link IResponse}实现
 * <p>
 * 子类只重写他们感兴趣的方法
 * </p>
 *
 * @author Created by 思伟 on 2021/3/19
 */
public abstract class IResponseAdapter<T> implements IResponse<T> {
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    public boolean isSuccess() {
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    public String getErrMsg() {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    public T getData() {
        return null;
    }
}
