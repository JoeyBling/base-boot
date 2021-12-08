package com.tynet.saas.common.service.adapter;

import com.tynet.saas.common.service.ICacheService;

import java.time.Duration;

/**
 * 允许带有空方法的{@link ICacheService}实现
 * <p>
 * 子类只重写他们感兴趣的方法
 * </p>
 *
 * @author Created by 思伟 on 2021/11/19
 * @since 1.8
 */
public interface ICacheServiceAdapter extends ICacheService {

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    default String getCacheName() {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    default Duration getCacheTime() {
        return null;
    }

}
