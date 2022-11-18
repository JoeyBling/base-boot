package com.tynet.saas.common.handler;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;

/**
 * 自定义缓存异常日志处理器
 * <p>
 * {@link org.springframework.cache.interceptor.AbstractCacheInvoker#getErrorHandler}
 * </p>
 *
 * @author Created by 思伟 on 2021/1/30
 */
public class LoggingCacheErrorHandler extends SimpleCacheErrorHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * ---------- 备注（扩展） ----------
     * <p>参考：
     * 1. {@link org.springframework.cache.interceptor.LoggingCacheErrorHandler}
     */

    /**
     * 是否引发异常 - Whether to throw an exception
     * <p>Defaults {@code true}.
     */
    @Setter
    protected boolean throwException = true;

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        logger.error(String.format("cacheName:%s,cacheKey:%s",
                cache == null ? "unknown" : cache.getName(), key), exception);
        if (this.isThrowException()) {
            // throw exception;
            // call super...
            super.handleCacheGetError(exception, cache, key);
        }
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key,
                                    Object value) {
        logger.error(String.format("cacheName:%s,cacheKey:%s",
                cache == null ? "unknown" : cache.getName(), key), exception);
        if (this.isThrowException()) {
            // call super...
            super.handleCachePutError(exception, cache, key, value);
        }
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        logger.error(String.format("cacheName:%s,cacheKey:%s",
                cache == null ? "unknown" : cache.getName(), key), exception);
        if (this.isThrowException()) {
            // call super...
            super.handleCacheEvictError(exception, cache, key);
        }
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        logger.error(String.format("cacheName:%s", cache == null ? "unknown" : cache.getName()),
                exception);
        if (this.isThrowException()) {
            // call super...
            super.handleCacheClearError(exception, cache);
        }
    }

    /**
     * 是否引发异常
     *
     * @return boolean
     */
    public boolean isThrowException() {
        return Boolean.TRUE.equals(throwException);
    }

}
