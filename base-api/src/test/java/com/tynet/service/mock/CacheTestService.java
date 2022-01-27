package com.tynet.service.mock;

import com.tynet.saas.common.hessian.IAppProperties;
import com.tynet.saas.common.service.adapter.ICacheServiceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * 缓存测试模拟服务
 *
 * @author Created by 思伟 on 2022/1/20
 */
@Service
@CacheConfig(cacheNames = {
        CacheTestService.CACHE_NAME,
})
public class CacheTestService implements ICacheServiceAdapter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 缓存名
     */
    protected static final String CACHE_NAME = "CACHE_TEST";

    @Autowired
    protected IAppProperties appProperties;
    @Autowired
    protected IAppProperties.CacheConfig cacheConfig;

    /**
     * 缓存空值
     *
     * @param key key
     * @return {@code null}
     */
    @Cacheable(key = "#root.target.cachePrefix + '_' + #key")
    public Object cacheNullVal(String key) {
        logger.debug("do `cacheNullVal`...");
        return null;
    }

    @Override
    public String getCacheName() {
        return CACHE_NAME;
    }

    @Override
    public Duration getCacheTime() {
        return appProperties.getCacheConfig().getExpire(this.getCacheName());
    }

}
