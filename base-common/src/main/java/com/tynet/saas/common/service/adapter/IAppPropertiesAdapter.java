package com.tynet.saas.common.service.adapter;

import com.tynet.saas.common.hessian.IAppProperties;

/**
 * 允许带有空方法的{@link IAppProperties}实现
 * <p>
 * 子类只重写他们感兴趣的方法
 * </p>
 *
 * @author Created by 思伟 on 2021/2/26
 */
public abstract class IAppPropertiesAdapter implements IAppProperties {

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    public String getClientId() {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    public String getDomain() {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    public CacheConfig getCacheConfig() {
        return null;
    }

}
