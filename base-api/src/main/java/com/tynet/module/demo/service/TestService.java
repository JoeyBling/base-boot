package com.tynet.module.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tynet.module.demo.dao.TestMapper;
import com.tynet.module.demo.domain.Test;
import com.tynet.saas.common.hessian.IAppProperties;
import com.tynet.saas.common.service.adapter.ICacheServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * 测试接口实现
 *
 * @author Created by 思伟 on 2022/1/12
 */
@Service
@CacheConfig(cacheNames = {
        TestService.CACHE_NAME,
})
public class TestService extends ServiceImpl<TestMapper, Test>
        implements ICacheServiceAdapter {
    /**
     * 缓存名
     */
    protected static final String CACHE_NAME = "TEST";

    @Autowired
    protected IAppProperties appProperties;

    @Override
    public String getCacheName() {
        return CACHE_NAME;
    }

    @Override
    public Duration getCacheTime() {
        return appProperties.getCacheConfig().getExpire(this.getCacheName());
    }

}
